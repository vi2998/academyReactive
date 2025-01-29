package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.exception.CustomException;
import it.reactive.torneoDemo.exception.SquadraDuplicataException;
import it.reactive.torneoDemo.exception.SquadraNonPresenteException;
import it.reactive.torneoDemo.exception.TorneoNonTrovatoException;
import it.reactive.torneoDemo.mapper.TorneoMapper;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_STATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_STATEMENT)

public class ITorneoDaoImplJDBCStatement implements ITorneoDao {


    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    ISquadraDaoImplJDBCStatement iSquadraDaoImplJDBCStatement;

    @Autowired
    IGiocatoreDao iGiocatoreDao;

    @Autowired
    ISquadraDao iSquadraDao;

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
    @Transactional
    public void eliminaTorneo(int idTorneo) throws SQLException {
        Connection con = null;
        Statement st = null;
        Statement statementSquadra = null;
        Statement statementSquadraTorneo = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());

            String querySquadre = "select id_squadra from squadra_torneo where id_torneo = " + idTorneo;
            st = con.createStatement();
            statementSquadra = con.createStatement();
            statementSquadraTorneo = con.createStatement();
            ResultSet rs = st.executeQuery(querySquadre);

            while (rs.next()) {
                int idSquadra = rs.getInt("id_squadra");

                String queryPresenzaSquadra = "select count(*) from squadra_torneo where id_squadra = " + idSquadra;
                ResultSet rsSquadra = statementSquadra.executeQuery(queryPresenzaSquadra);

                if (rsSquadra.next() && rsSquadra.getInt(1) == 1) {
                    statementSquadraTorneo.executeUpdate("delete from squadra_torneo where id_squadra='" + idSquadra + "'");
                    iSquadraDao.rimuoviSquadra(idSquadra);
                } else {
                    // Se la squadra appare in altri tornei, rimuovi solo la relazione con questo torneo
                    String queryDeleteSquadraTorneo = "delete from squadra_torneo where id_squadra = " + idSquadra + " and id_torneo = " + idTorneo;
                    statementSquadraTorneo.executeUpdate(queryDeleteSquadraTorneo);
                }
            }

            String queryDeleteTorneo = "delete from torneo where id = " + idTorneo;
            st.executeUpdate(queryDeleteTorneo);


        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'eliminazione del torneo", e);
        }finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
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
        Statement st = null;

        List<TorneoModel> torneoModelList = new ArrayList<>();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String query = "select * from torneo";
            st = con.createStatement();
            rs = st.executeQuery(query);
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

    public Set<SquadraModel> recuperaSquadreByIdTorneo(int id) {
        Connection con = null;
        Set<SquadraModel> squadraModelList = new HashSet<>();
        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            Statement st = con.createStatement();
            ResultSet rsSquadre = st.executeQuery("SELECT s.nome, s.id,s.colori_sociali FROM squadra_torneo st JOIN " +
                    "squadra s ON s.id = st" +
                    ".id_squadra WHERE st.id_torneo = " + id);
            while (rsSquadre.next()) {
                SquadraModel squadraModel = new SquadraModel();
                squadraModel.setNome(rsSquadre.getString("nome"));
                squadraModel.setIdSquadra(rsSquadre.getInt("id"));
                squadraModel.setColoriSociali(rsSquadre.getString("colori_sociali"));
                squadraModel.setGiocatori(iSquadraDaoImplJDBCStatement.getGiocatoriBySquadra(rsSquadre.getInt("id")));
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
