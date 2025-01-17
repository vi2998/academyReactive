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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_STATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_STATEMENT)

public class ITorneoDaoImplJDBCStatement implements ITorneoDao {


    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        TorneoModel torneoModel = new TorneoModel();


        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            String insertQuery = "INSERT INTO torneo (nome_torneo) VALUES ('" + torneoDTO.getNomeTorneo() + "')";
            st.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();

            if (rs.next()) {
                int idTorneo = rs.getInt("id");
                torneoModel.setIdTorneo(idTorneo);
                torneoModel.setNomeTorneo(torneoDTO.getNomeTorneo());
            } else {
                System.out.println("Qualcosa è andato storto. La chiave non è stata generata.");
            }

            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }

            return torneoModel;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminaTorneo(int idTorneo) throws SQLException {
        //FIXME: cancella TORNEO, squadra e giocatori per IL TORNEO cancellatO SE LA SQUADRA NON è PRESENTE IN ALTRI TORNEI
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
        st = con.createStatement();

        st.executeUpdate("DELETE FROM squadra_torneo WHERE id_torneo = " + idTorneo);
        st.executeUpdate("DELETE FROM giocatore WHERE id_squadra IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");
        st.executeUpdate("DELETE FROM tifoseria WHERE id_squadra IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");
        st.executeUpdate("DELETE FROM squadra WHERE id IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");

        int nRow = st.executeUpdate("delete from torneo where id= " + idTorneo);
        if (nRow == 0) {
            System.out.println("Qualcosa è andato storto");
        }
    }

    @Override
    public TorneoModel associaTorneoASquadra(int idTorneo, int idSquadra) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        TorneoModel torneoModel = new TorneoModel();
        Set<SquadraModel> squadraModelSet = new HashSet<>();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            String query = "SELECT * FROM squadra WHERE id = " + idSquadra;
            rs = st.executeQuery(query);
            if (!rs.next()) {
                throw new SquadraNonPresenteException();
            }

            SquadraModel squadraModel = new SquadraModel();
            squadraModel.setIdSquadra(rs.getInt("id"));
            squadraModel.setNome(rs.getString("nome"));
            squadraModel.setColoriSociali(rs.getString("colori_sociali"));

            squadraModelSet.add(squadraModel);

            query = "SELECT * FROM torneo WHERE id = " + idTorneo;
            rs = st.executeQuery(query);
            if (!rs.next()) {
                throw new TorneoNonTrovatoException();
            }

            torneoModel.setIdTorneo(rs.getInt("id"));
            torneoModel.setNomeTorneo(rs.getString("nome_torneo"));

            torneoModel.setSquadre(squadraModelSet);

            String queryInsert = "INSERT INTO squadra_torneo (id_torneo, id_squadra) VALUES (" + idTorneo + ", " + idSquadra + ")";
            int numeroRiga = st.executeUpdate(queryInsert);

            if (numeroRiga != 1) {
                throw new SQLException("Qualcosa è andato storto nell'inserimento nella tabella squadra_torneo.");
            }

            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
            return torneoModel;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<TorneoModel> cercaTorneiAndSquadre() throws SQLException {
        /* TODO: senza input restituisce l'elenco di tutti i torneo con la lista delle squadre partecipanti ad ogni torneo.
            Per ogni squadra le informazioni sul nome della tifoseria e la lista dei giocatori con nome e numero di ammonizioni
            (usare una nativequery con le join tra le tabelle).
            Prima di fornire la risposta dovrà essere contatta
            la banca nazionale TransferMarket all'indirizzo http://85.235.148.177:8872/transfer/{nomegiocatore}
            che restituirà lo storico dei trasferimenti del giocatore. Quindi nella risorsa giocatore predisporsi
            quindi per ottenere anche una lista di oggetti con attributi anno e squadra. */

        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            String query = "\n" +
                    "SELECT \n" +
                    "    t.nome_torneo,\n" +
                    "    s.nome AS nome_squadra,\n" +
                    "    ts.nome_tifoseria,\n" +
                    "    g.nome_cognome AS nome_giocatore,\n" +
                    "    g.numero_ammonizioni\n" +
                    "FROM torneo t\n" +
                    "JOIN squadra_torneo st ON t.id = st.id_torneo\n" +
                    "JOIN squadra s ON st.id_squadra = s.id\n" +
                    "LEFT JOIN tifoseria ts ON s.id = ts.id_squadra\n" +
                    "LEFT JOIN giocatore g ON s.id = g.id_squadra\n" +
                    "ORDER BY t.nome_torneo, s.nome, g.numero_ammonizioni;";


            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return Collections.emptyList();
    }


}
