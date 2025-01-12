package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.exception.GiocatoreDuplicatoException;
import it.reactive.torneoDemo.exception.SquadraDuplicataException;
import it.reactive.torneoDemo.exception.SquadraNonPresenteException;
import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TifoseriaModel;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_STATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_STATEMENT)

public class ISquadraDaoImplJDBCStatement implements ISquadraDao {

    //FIXME CORREGGERE: IL GIOCATORE NON PUò STARE IN PIù SQUADRE --> DI CONSEGUENZA ANCHE TIFOSERIA E TORNEI
    @Autowired
    ConfigurazioneDB configurazioneDB;

    @Override
    public SquadraModel aggiungiGiocatore(int idSquadra, GiocatoreDTO giocatoreDTO) throws SQLException {

        // Crea connessione
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        // Query per recuperare i giocatori già presenti nella squadra
        String query = "SELECT nome_cognome FROM giocatore WHERE id_squadra = " + idSquadra;
        ResultSet rs = st.executeQuery(query);

        List<GiocatoreModel> giocatoriGiaPresenti = new ArrayList<>();
        while (rs.next()) {
            GiocatoreModel giocatoreModel = new GiocatoreModel();
            giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
            giocatoriGiaPresenti.add(giocatoreModel);
        }

        // Verifica se il giocatore è già presente nella lista
        for (GiocatoreModel giocatoreModel : giocatoriGiaPresenti) {
            if (giocatoreModel.getNomeCognome().equalsIgnoreCase(giocatoreDTO.getNomeCognome())) {
                throw new GiocatoreDuplicatoException();
            }
        }

        // Inserisci il nuovo giocatore
        String insertQuery = "INSERT INTO giocatore (nome_cognome, id_squadra) VALUES ('" + giocatoreDTO.getNomeCognome() + "', " + idSquadra + ")";
        st.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
        rs = st.getGeneratedKeys();
        rs.next();
        int idGiocatore = rs.getInt("id");

        // Recupera la squadra
        String selectSquadraQuery = "SELECT * FROM squadra WHERE id = " + idSquadra;
        rs = st.executeQuery(selectSquadraQuery);
        SquadraModel squadraModel = new SquadraModel();
        if (rs.next()) {
            squadraModel.setNome(rs.getString("nome"));
            squadraModel.setColoriSociali(rs.getString("colori_sociali"));
        } else {
            throw new SquadraNonPresenteException();
        }
        // Aggiungi il nuovo giocatore alla lista dei giocatori
        GiocatoreModel nuovoGiocatore = new GiocatoreModel();
        nuovoGiocatore.setNomeCognome(giocatoreDTO.getNomeCognome());
        nuovoGiocatore.setIdGiocatore(idGiocatore);
        giocatoriGiaPresenti.add(nuovoGiocatore);

        // Imposta i giocatori nella squadra
        squadraModel.setGiocatori(new HashSet<>(giocatoriGiaPresenti));
        squadraModel.setIdSquadra(idSquadra);
        con.commit();
        con.close();
        return squadraModel;
    }

    @Override
    public SquadraModel aggiungiTifoseria(int idSquadra, TifoseriaDTO tifoseriaDTO) throws SQLException {
        SquadraModel squadraModel = new SquadraModel();
        TifoseriaModel tifoseriaModel = new TifoseriaModel();

        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        try {
            String querySelect = "SELECT nome_tifoseria FROM tifoseria WHERE id_squadra = " + idSquadra;
            ResultSet rs = st.executeQuery(querySelect);

            if (rs.next()) {
                // Se la tifoseria esiste
                String queryUpdate = "UPDATE tifoseria SET nome_tifoseria = '" + tifoseriaDTO.getNomeTifoseria() + "' WHERE id_squadra = " + idSquadra;
                st.executeUpdate(queryUpdate);
            } else {
                // Se non esiste
                String queryInsert = "INSERT INTO tifoseria (nome_tifoseria, id_squadra) VALUES ('" + tifoseriaDTO.getNomeTifoseria() + "', " + idSquadra + ")";
                st.executeUpdate(queryInsert);
            }

            String querySquadra = "SELECT nome FROM squadra WHERE id = " + idSquadra;
            rs = st.executeQuery(querySquadra);

            if (rs.next()) {
                String nomeSquadra = rs.getString("nome");
                squadraModel.setNome(nomeSquadra);
                squadraModel.setTifoseria(tifoseriaModel);
            }
            con.commit();
        } catch (SQLException e) {
            throw new SQLException("É stato riscontrato un errore");
        }
        con.commit();
        con.close();
        return squadraModel;
    }


    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        int numeroRiga = 0;
        int idSquadra;
        try {
            String insertQuery = "insert into squadra (nome, colori_sociali) values ('" + squadraDTO.getNome() + "','" + squadraDTO.getColoriSociali() + "')";
            numeroRiga = st.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            idSquadra = rs.getInt("id");
        } catch (SQLException e) {
            throw new SquadraDuplicataException();
        }
        if (numeroRiga != 1) {
            System.out.println("Qualcosa è andato storto");
        } else {
            con.commit();
        }
        SquadraModel squadraModel = new SquadraModel();
        squadraModel.setIdSquadra(idSquadra);
        squadraModel.setNome(squadraDTO.getNome());
        squadraModel.setColoriSociali(squadraDTO.getColoriSociali());
        con.close();
        return squadraModel;
    }

    @Override
    public void rimuoviSquadra(int id) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        st.executeUpdate("delete from giocatore where id_squadra='" + id + "'");
        st.executeUpdate("delete from tifoseria where id_squadra='" + id + "'");
        int nRow = st.executeUpdate("delete from squadra where id='" + id + "'");
        if (nRow == 0) {
            throw new SquadraNonPresenteException();
        }
        con.commit();
        con.close();
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean conGiocatori) throws SQLException {
        //fixme:
        // Nello Step2 e nell'implementazione JPA fare la ricerca con una Query (non native) nel caso di completo e
        // NamedQuery nel caso di non completo.

        Connection con = configurazioneDB.init();
        List<SquadraModel> squadraModelList = new ArrayList<>();
        Statement st = con.createStatement();
        String query = "select s.id, nome, colori_sociali, t.nome_tifoseria from squadra s join tifoseria t on s.id = t.id_squadra";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            int idSquadra = rs.getInt("id");
            SquadraModel squadraModel = new SquadraModel();
            squadraModel.setIdSquadra(idSquadra);
            squadraModel.setNome(rs.getString("nome"));
            squadraModel.setColoriSociali(rs.getString("colori_sociali"));
            squadraModel.setTifoseria(getTifoseriaSquadraBySquadra(idSquadra));


            // se true aggiungo giocatori alla squadra
            if (conGiocatori) {
                squadraModel.setGiocatori(getGiocatoriBySquadra(idSquadra));
            }
            squadraModelList.add(squadraModel);
        }
        con.commit();
        con.close();
        return squadraModelList;
    }

    private Set<GiocatoreModel> getGiocatoriBySquadra(int idSquadra) throws SQLException {
        Set<GiocatoreModel> giocatoreModelSet = new HashSet<>();
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();
        String query = "SELECT g.id, g.nome_cognome FROM giocatore as g JOIN squadra as sq ON g.id_squadra = sq.id WHERE g.id_squadra = " + idSquadra;
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            GiocatoreModel giocatoreModel = new GiocatoreModel();
            giocatoreModel.setIdGiocatore(rs.getInt("id"));
            giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
            giocatoreModelSet.add(giocatoreModel);
        }
        return giocatoreModelSet;
    }

    public TifoseriaModel getTifoseriaSquadraBySquadra(int idSquadra) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();
        TifoseriaModel tifoseriaModel = new TifoseriaModel();

        String query = "select * from tifoseria where id_squadra = " + idSquadra;
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            tifoseriaModel.setIdTifoseria(rs.getInt("id"));
            tifoseriaModel.setNomeTifoseria(rs.getString("nome_tifoseria"));
        }
        return tifoseriaModel;

    }
}