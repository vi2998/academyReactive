package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
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

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_STATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_STATEMENT)
public class GiocatoreDaoImplJDBCStatement implements IGiocatoreDao {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public GiocatoreModel aggiornaAmmonizione(Integer idGiocatore) throws SQLException {
        Connection con = null;
        GiocatoreModel giocatoreModel = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            String query = "SELECT g.id, g.nome_cognome, g.numero_ammonizioni from giocatore as g where g.id = " + idGiocatore;
            rs = st.executeQuery(query);

            if (rs.next()) {
                giocatoreModel = new GiocatoreModel();
                giocatoreModel.setIdGiocatore(rs.getInt("id"));
                giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
                giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
                giocatoreModel.setNumeroAmmonizioni(giocatoreModel.getNumeroAmmonizioni() + 1);

            } else {
                throw new SQLException("Giocatore non trovato con ID: " + idGiocatore);
            }

            String queryUpdate = "UPDATE giocatore SET numero_ammonizioni = " +
                    giocatoreModel.getNumeroAmmonizioni() + " WHERE id = " + giocatoreModel.getIdGiocatore();
            int numeroRiga = st.executeUpdate(queryUpdate);
            if (numeroRiga != 1) {
                System.out.println("Qualcosa è andato storto nell'aggiornamento dell'ammonizione.");
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
        return giocatoreModel;
    }
}
