package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.exception.GiocatoreDuplicatoException;
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
        st.executeUpdate(insertQuery);

        // Recupera la squadra aggiornata con i giocatori
        String selectSquadraQuery = "SELECT * FROM squadra WHERE id = " + idSquadra;
        rs = st.executeQuery(selectSquadraQuery);
        SquadraModel squadraModel = new SquadraModel();

        if (rs.next()) {
            squadraModel.setNome(rs.getString("nome"));
            squadraModel.setColoriSociali(rs.getString("colori_sociali"));
        }

        // Aggiungi il nuovo giocatore alla lista dei giocatori
        GiocatoreModel nuovoGiocatore = new GiocatoreModel();
        nuovoGiocatore.setNomeCognome(giocatoreDTO.getNomeCognome());
        giocatoriGiaPresenti.add(nuovoGiocatore);

        // Imposta i giocatori nella squadra
        squadraModel.setGiocatori(new HashSet<>(giocatoriGiaPresenti));
        con.commit();
        return squadraModel;
    }

    @Override   //FIXME RESPONSE BODY NELLO SWAGGER
    public SquadraModel aggiungiTifoseria(int idSquadra, TifoseriaDTO tifoseriaDTO) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        String insertQuery = "INSERT INTO tifoseria (nome_tifoseria, id_squadra) VALUES ('" + tifoseriaDTO.getNomeTifoseria() + "', " + idSquadra + ")";
        st.executeUpdate(insertQuery);

        String query = "SELECT s.nome, t.nome_tifoseria FROM squadra s , tifoseria t WHERE s.id = t.id_squadra";
        ResultSet rs = st.executeQuery(query);

        SquadraModel squadraModel = new SquadraModel();

        if (rs.next()) {
            String nomeSquadra = rs.getString("nome");
            String nomeTifoseria = rs.getString("nome_tifoseria");

            squadraModel.setNome(nomeSquadra);
            TifoseriaModel tifoseriaModel = new TifoseriaModel();
            tifoseriaModel.setNomeTifoseria(nomeTifoseria);
            tifoseriaModel.setSquadra(squadraModel);
            squadraModel.setTifoseria(tifoseriaModel);
        }
        con.commit();
        return squadraModel;
    }

    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) {
        return null;
    }

    @Override
    public SquadraModel salvaSquadraDiGiocatori(SquadreDiGiocatoriDTO squadreDiGiocatoriDTO) {
        return null;
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
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori) {
        return Collections.emptyList();
    }

}