package it.reactive.torneoDemo.repository.jdbcPreparedStatement;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.exception.CustomException;
import it.reactive.torneoDemo.exception.SquadraDuplicataException;
import it.reactive.torneoDemo.exception.SquadraNonPresenteException;
import it.reactive.torneoDemo.exception.TorneoNonTrovatoException;
import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TifoseriaModel;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.*;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_PREPAREDSTATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_PREPAREDSTATEMENT)
public class TorneoDaoImplJDBCPreparedStatement implements ITorneoDao {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    ISquadraDao iSquadraDao;

    @Autowired
    SquadraDaoImplJDBCPreparedStatement squadraDaoImplJDBCPreparedStatement;

    @Override
    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        TorneoModel torneoModel = new TorneoModel();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String insertQuery = "INSERT INTO torneo (nome_torneo) VALUES (?)";
            ps = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, torneoDTO.getNomeTorneo());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int idTorneo = rs.getInt(1);
                torneoModel.setIdTorneo(idTorneo);
                torneoModel.setNomeTorneo(torneoDTO.getNomeTorneo());
            } else {
                System.out.println("Qualcosa è andato storto. La chiave non è stata generata.");
            }

            return torneoModel;

        } catch (SQLException e) {
            if ("23505".equalsIgnoreCase(e.getSQLState())) {
                throw new CustomException("C1", "Torneo già censito");
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }


    @Override
    public void eliminaTorneo(int idTorneo) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());

            String querySquadre = "SELECT id_squadra FROM squadra_torneo WHERE id_torneo = ?";
            ps = con.prepareStatement(querySquadre);
            ps.setInt(1, idTorneo);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idSquadra = rs.getInt("id_squadra");

                String queryPresenzaSquadra = "SELECT COUNT(*) FROM squadra_torneo WHERE id_squadra = ?";
                PreparedStatement psSquadra = con.prepareStatement(queryPresenzaSquadra);
                psSquadra.setInt(1, idSquadra);
                ResultSet rsSquadra = psSquadra.executeQuery();

                if (rsSquadra.next() && rsSquadra.getInt(1) == 1) {
                    String deleteSquadraTorneo = "DELETE FROM squadra_torneo WHERE id_squadra = ?";
                    PreparedStatement psDeleteSquadraTorneo = con.prepareStatement(deleteSquadraTorneo);
                    psDeleteSquadraTorneo.setInt(1, idSquadra);
                    psDeleteSquadraTorneo.executeUpdate();

                    iSquadraDao.rimuoviSquadra(idSquadra);
                } else {
                    // Se la squadra appare in altri tornei, rimuovi solo la relazione con questo torneo
                    String deleteSquadraTorneo = "DELETE FROM squadra_torneo WHERE id_squadra = ? AND id_torneo = ?";
                    PreparedStatement psDeleteSquadraTorneo = con.prepareStatement(deleteSquadraTorneo);
                    psDeleteSquadraTorneo.setInt(1, idSquadra);
                    psDeleteSquadraTorneo.setInt(2, idTorneo);
                    psDeleteSquadraTorneo.executeUpdate();
                }
            }

            String queryDeleteTorneo = "DELETE FROM torneo WHERE id = ?";
            PreparedStatement psDeleteTorneo = con.prepareStatement(queryDeleteTorneo);
            psDeleteTorneo.setInt(1, idTorneo);
            psDeleteTorneo.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'eliminazione del torneo", e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }

    @Override
    public TorneoModel associaTorneoASquadra(int idTorneo, int idSquadra) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        TorneoModel torneoModel = new TorneoModel();
        Set<SquadraModel> squadraModelSet = new HashSet<>();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String querySquadra = "SELECT * FROM squadra WHERE id = ?";
            ps = con.prepareStatement(querySquadra);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();

            if (!rs.next()) {
                throw new SquadraNonPresenteException();
            }

            SquadraModel squadraModel = new SquadraModel();
            squadraModel.setIdSquadra(rs.getInt("id"));
            squadraModel.setNome(rs.getString("nome"));
            squadraModel.setColoriSociali(rs.getString("colori_sociali"));
            squadraModel.setGiocatori(squadraDaoImplJDBCPreparedStatement.getGiocatoriBySquadra(rs.getInt("id")));
            squadraModel.setTifoseria(squadraDaoImplJDBCPreparedStatement.getTifoseriaSquadraBySquadra(rs.getInt("id")));

            squadraModelSet.add(squadraModel);

            String queryTorneo = "SELECT * FROM torneo WHERE id = ?";
            ps = con.prepareStatement(queryTorneo);
            ps.setInt(1, idTorneo);
            rs = ps.executeQuery();

            if (!rs.next()) {
                throw new TorneoNonTrovatoException();
            }

            torneoModel.setIdTorneo(rs.getInt("id"));
            torneoModel.setNomeTorneo(rs.getString("nome_torneo"));

            torneoModel.setSquadre(squadraModelSet);

            String queryInsert = "INSERT INTO squadra_torneo (id_torneo, id_squadra) VALUES (?, ?)";
            ps = con.prepareStatement(queryInsert);
            ps.setInt(1, idTorneo);
            ps.setInt(2, idSquadra);
            int numeroRiga = ps.executeUpdate();

            if (numeroRiga != 1) {
                throw new SQLException("Qualcosa è andato storto nell'inserimento nella tabella squadra_torneo.");
            }

            return torneoModel;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }

    @Override
    public List<TorneoModel> cercaTorneiAndSquadre() throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<TorneoModel> torneoModelList = new ArrayList<>();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String query = "SELECT * FROM torneo";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                TorneoModel torneo = new TorneoModel();
                torneo.setIdTorneo(rs.getInt("id"));
                torneo.setNomeTorneo(rs.getString("nome_torneo"));
                torneo.setSquadre(recuperaSquadreByIdTorneo(rs.getInt("id")));
                torneoModelList.add(torneo);
            }

            return torneoModelList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }

    public Set<SquadraModel> recuperaSquadreByIdTorneo(int id) throws SQLException {
        Connection con = null;
        Set<SquadraModel> squadraModelList = new HashSet<>();
        PreparedStatement ps = null;
        ResultSet rsSquadre = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String query = "SELECT s.nome, s.id, s.colori_sociali FROM squadra_torneo st JOIN squadra s ON s.id = st.id_squadra WHERE st.id_torneo = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rsSquadre = ps.executeQuery();

            while (rsSquadre.next()) {
                SquadraModel squadraModel = new SquadraModel();
                squadraModel.setNome(rsSquadre.getString("nome"));
                squadraModel.setIdSquadra(rsSquadre.getInt("id"));
                squadraModel.setColoriSociali(rsSquadre.getString("colori_sociali"));
                squadraModel.setGiocatori(squadraDaoImplJDBCPreparedStatement.getGiocatoriBySquadra(rsSquadre.getInt("id")));
                squadraModel.setTifoseria(squadraDaoImplJDBCPreparedStatement.getTifoseriaSquadraBySquadra(rsSquadre.getInt("id")));
                squadraModelList.add(squadraModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
        return squadraModelList;
    }
}
