package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.SquadreDiGiocatoriDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.exception.GiocatoreDuplicatoException;
import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
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

    @Override
    public SquadraModel aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) {
        return null;
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
    public SquadraModel rimuoviSquadra(int id) {
        return null;
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori) {
        return Collections.emptyList();
    }

}