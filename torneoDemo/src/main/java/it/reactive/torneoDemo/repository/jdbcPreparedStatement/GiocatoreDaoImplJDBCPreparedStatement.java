package it.reactive.torneoDemo.repository.jdbcPreparedStatement;

import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.*;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_PREPAREDSTATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_PREPAREDSTATEMENT)
public class GiocatoreDaoImplJDBCPreparedStatement implements IGiocatoreDao {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public GiocatoreModel aggiornaAmmonizione(Integer idGiocatore) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        GiocatoreModel giocatoreModel = new GiocatoreModel();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String querySelect = "SELECT g.id, g.nome_cognome, g.numero_ammonizioni, s.nome AS nome_squadra " +
                    "FROM giocatore g " +
                    "JOIN squadra s ON g.id_squadra = s.id " +
                    "WHERE g.id = ?";
            ps = con.prepareStatement(querySelect);
            ps.setInt(1, idGiocatore);

            rs = ps.executeQuery();

            if (rs.next()) {
                giocatoreModel = new GiocatoreModel();
                giocatoreModel.setIdGiocatore(rs.getInt("id"));
                giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
                giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));

                SquadraModel squadraModel = new SquadraModel();
                squadraModel.setNome(rs.getString("nome_squadra"));
                giocatoreModel.setSquadra(squadraModel);

                giocatoreModel.setNumeroAmmonizioni(giocatoreModel.getNumeroAmmonizioni() + 1);
            } else {
                throw new SQLException("Giocatore non trovato con ID: " + idGiocatore);
            }

            String queryUpdate = "UPDATE giocatore SET numero_ammonizioni = ? WHERE id = ?";
            PreparedStatement psUpdate = con.prepareStatement(queryUpdate);
            psUpdate.setInt(1, giocatoreModel.getNumeroAmmonizioni());
            psUpdate.setInt(2, giocatoreModel.getIdGiocatore());

            int numeroRiga = psUpdate.executeUpdate();
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
