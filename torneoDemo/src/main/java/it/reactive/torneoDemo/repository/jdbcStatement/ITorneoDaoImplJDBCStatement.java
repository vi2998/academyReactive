package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.Costanti;
import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.exception.SquadraNonPresenteException;
import it.reactive.torneoDemo.exception.TorneoNonTrovatoException;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_STATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_STATEMENT)

public class ITorneoDaoImplJDBCStatement implements ITorneoDao {

    @Autowired
    ConfigurazioneDB configurazioneDB;

    @Override
    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        String insertQuery = "INSERT INTO torneo (nome_torneo) VALUES ('" + torneoDTO.getNomeTorneo() + "')";
        int numeroRiga = st.executeUpdate(insertQuery);
        if (numeroRiga != 1) {
            System.out.println("Qualcosa è andato storto");
        }else{
            con.commit();
        }
        TorneoModel torneoModel = new TorneoModel();
        torneoModel.setNomeTorneo(torneoDTO.getNomeTorneo());
        con.close();
        return torneoModel;
    }

    @Override
    public void eliminaTorneo(int idTorneo) throws SQLException {
        //FIXME: cancella TORNEO, squadra e giocatori per IL TORNEO cancellatO SE LA SQUADRA NON è PRESENTE IN ALTRI TORNEI
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        st.executeUpdate("DELETE FROM squadra_torneo WHERE id_torneo = " + idTorneo);
        st.executeUpdate("DELETE FROM giocatore WHERE id_squadra IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");
        st.executeUpdate("DELETE FROM tifoseria WHERE id_squadra IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");
        st.executeUpdate("DELETE FROM squadra WHERE id IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");

        int nRow = st.executeUpdate("delete from torneo where id= " + idTorneo);
        if (nRow == 0) {
            System.out.println("Qualcosa è andato storto");
        }
        con.commit();
        con.close();
    }

    @Override
    public TorneoModel associaTorneoASquadra(int idTorneo, int idSquadra) throws SQLException {
        //FIXME: Per questo metodo usare una namedQuery per cercare squadra e torneo.
        // Se non trova il torneo restituisce eccezione TORNONONTROVATOEXC",
        // se non trova la squadra C4 SQUADRANONPRESENTEEXC".
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();
        TorneoModel torneoModel = new TorneoModel();

        try {
            // 1. Controllo se la squadra è presente
            String query = "SELECT id FROM squadra WHERE id = " + idSquadra;
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) {
                throw new SquadraNonPresenteException();
            }

            // 2. Controllo se il torneo è presente
            query = "SELECT * FROM torneo WHERE id = " + idTorneo;
            rs = st.executeQuery(query);
            if (!rs.next()) {
                throw new TorneoNonTrovatoException();
            }

            // 3. Popola il modello del torneo
            torneoModel.setIdTorneo(rs.getInt("id"));
            torneoModel.setNomeTorneo(rs.getString("nome_torneo"));

            // 4. Inserimento nella tabella di collegamento (squadra_torneo)
            String queryInsert = "INSERT INTO squadra_torneo (id_torneo, id_squadra) VALUES (" + idTorneo + ", " + idSquadra + ")";
            int numeroRiga = st.executeUpdate(queryInsert);

            if (numeroRiga != 1) {
                throw new SQLException("Qualcosa è andato storto durante l'inserimento nella tabella squadra_torneo.");
            } else {
                con.commit();
                con.close();
            }

        } catch (SQLException e) {
            System.out.println("Qualcosa è andato storto");
        }

        return torneoModel;

    }


    @Override
    public List<TorneoModel> ricavoITornei() throws SQLException {
        /* FIXME: senza input restituisce l'elenco di tutti i torneo con la lista delle squadre partecipanti ad ogni torneo.
            Per ogni squadra le informazioni sul nome della tifoseria e la lista dei giocatori con nome e numero di ammonizioni
            (usare una nativequery con le join tra le tabelle).
            Prima di fornire la risposta dovrà essere contatta
            la banca nazionale TransferMarket all'indirizzo http://85.235.148.177:8872/transfer/{nomegiocatore}
            che restituirà lo storico dei trasferimenti del giocatore. Quindi nella risorsa giocatore predisporsi
            quindi per ottenere anche una lista di oggetti con attributi anno e squadra. */
        return Collections.emptyList();
    }


}
