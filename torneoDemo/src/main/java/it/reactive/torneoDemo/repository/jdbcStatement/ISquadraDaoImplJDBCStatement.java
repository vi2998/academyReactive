package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

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
    PlatformTransactionManager transactionManager;

    @Override
    public SquadraModel aggiungiGiocatore(int idSquadra, GiocatoreDTO giocatoreDTO) throws SQLException {

        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();

            //verifica se è duplicato
            String query = "SELECT COUNT(*) FROM giocatore WHERE id_squadra = " + idSquadra + " AND nome_cognome = '" + giocatoreDTO.getNomeCognome() + "'";
            rs = st.executeQuery(query);
            if (rs.next() && rs.getInt(1) > 0) {
                throw new GiocatoreDuplicatoException();
            }

            String insertQuery = "INSERT INTO giocatore (nome_cognome, id_squadra) VALUES ('" + giocatoreDTO.getNomeCognome() + "', " + idSquadra + ")";
            st.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();
            rs.next();
            int idGiocatore = rs.getInt("id");

            // Recupera la squadra con tifoseria
            String selectQuerySquadra = "SELECT s.nome, s.colori_sociali, t.id AS tifoseria_id, t.nome_tifoseria " +
                    "FROM squadra s LEFT JOIN tifoseria t ON s.id = t.id_squadra WHERE s.id = " + idSquadra;
            rs = st.executeQuery(selectQuerySquadra);
            SquadraModel squadraModel = new SquadraModel();
            TifoseriaModel tifoseriaModel = new TifoseriaModel();
            if (rs.next()) {
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                tifoseriaModel.setIdTifoseria(rs.getInt("tifoseria_id"));
                tifoseriaModel.setNomeTifoseria(rs.getString("nome_tifoseria"));
                squadraModel.setTifoseria(tifoseriaModel);
            } else {
                throw new SquadraNonPresenteException();
            }

            String selectGiocatore = "SELECT id, nome_cognome, numero_ammonizioni FROM giocatore WHERE id_squadra = " + idSquadra;
            rs = st.executeQuery(selectGiocatore);

            Set<GiocatoreModel> giocatoriGiaPresenti = new HashSet<>();

            while (rs.next()) {
                GiocatoreModel giocatoreModel = new GiocatoreModel();
                giocatoreModel.setIdGiocatore(rs.getInt("id"));
                giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
                giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
                giocatoriGiaPresenti.add(giocatoreModel);

            }

            squadraModel.setGiocatori(giocatoriGiaPresenti);
            squadraModel.setIdSquadra(idSquadra);

            return squadraModel;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }


    @Override
    public SquadraModel aggiungiTifoseria(int idSquadra, TifoseriaDTO tifoseriaDTO) throws SQLException {
        SquadraModel squadraModel = new SquadraModel();
        TifoseriaModel tifoseriaModel = new TifoseriaModel();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();

            String querySelect = "SELECT id, nome_tifoseria FROM tifoseria WHERE id_squadra = " + idSquadra;
            rs = st.executeQuery(querySelect);

            if (rs.next()) {
                // Se la tifoseria esiste, aggiorna il nome
                int idTifoseria = rs.getInt("id");
                String queryUpdate = "UPDATE tifoseria SET nome_tifoseria = '" + tifoseriaDTO.getNomeTifoseria() + "' WHERE id_squadra = " + idSquadra;
                st.executeUpdate(queryUpdate);
                tifoseriaModel.setIdTifoseria(idTifoseria);
                tifoseriaModel.setNomeTifoseria(tifoseriaDTO.getNomeTifoseria());
            } else {
                // inserisci una nuova tifoseria
                String queryInsert = "INSERT INTO tifoseria (nome_tifoseria, id_squadra) VALUES ('" + tifoseriaDTO.getNomeTifoseria() + "', " + idSquadra + ")";
                st.executeUpdate(queryInsert, Statement.RETURN_GENERATED_KEYS);
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    tifoseriaModel.setIdTifoseria(rs.getInt("id"));
                    tifoseriaModel.setNomeTifoseria(tifoseriaDTO.getNomeTifoseria());
                }
            }

            String querySquadra = "SELECT nome, colori_sociali FROM squadra WHERE id = " + idSquadra;
            rs = st.executeQuery(querySquadra);
            if (rs.next()) {
                squadraModel.setIdSquadra(idSquadra);
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
            }

            String selectGiocatore = "SELECT id, nome_cognome, numero_ammonizioni FROM giocatore WHERE id_squadra = " + idSquadra;
            rs = st.executeQuery(selectGiocatore);

            Set<GiocatoreModel> giocatoriPresenti = new HashSet<>();

            while (rs.next()) {
                GiocatoreModel giocatoreModel = new GiocatoreModel();
                giocatoreModel.setIdGiocatore(rs.getInt("id"));
                giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
                giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
                giocatoriPresenti.add(giocatoreModel);
            }
            squadraModel.setTifoseria(tifoseriaModel);
            squadraModel.setGiocatori(giocatoriPresenti);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
        return squadraModel;
    }


    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException {
        Connection con = null;
        GiocatoreModel giocatoreModel = null;
        ResultSet rs = null;
        Statement st = null;
        int numeroRiga = 0;
        int idSquadra = 0;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            String insertQuery = "insert into squadra (nome, colori_sociali) values ('" + squadraDTO.getNome() + "','" + squadraDTO.getColoriSociali() + "')";
            numeroRiga = st.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                idSquadra = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new SquadraDuplicataException();
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
        if (numeroRiga != 1) {
            System.out.println("Qualcosa è andato storto");
        }
        SquadraModel squadraModel = new SquadraModel();
        squadraModel.setIdSquadra(idSquadra);
        squadraModel.setNome(squadraDTO.getNome());
        squadraModel.setColoriSociali(squadraDTO.getColoriSociali());

        return squadraModel;
    }

    @Override
    public void rimuoviSquadra(int id) throws SQLException {
        Connection connection = null;
        Statement st;
        try {
            connection = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = connection.createStatement();

            st.executeUpdate("delete from giocatore where id_squadra='" + id + "'");
            st.executeUpdate("delete from tifoseria where id_squadra='" + id + "'");
            int nRow = st.executeUpdate("delete from squadra where id='" + id + "'");
            if (nRow == 0) {
                throw new SquadraNonPresenteException();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean conGiocatori) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        List<SquadraModel> squadraModelList = new ArrayList<>();
        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            String query = "select s.id, nome, colori_sociali, t.nome_tifoseria from squadra s left join tifoseria t on s.id = t.id_squadra";
            rs = st.executeQuery(query);
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
        return squadraModelList;

    }

    public Set<GiocatoreModel> getGiocatoriBySquadra(int idSquadra) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;
        Set<GiocatoreModel> giocatoreModelSet = new HashSet<>();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            String query = "SELECT g.id, g.nome_cognome, g.numero_ammonizioni FROM giocatore as g JOIN squadra as sq ON g.id_squadra = sq.id WHERE g.id_squadra = " + idSquadra;
            rs = st.executeQuery(query);
            while (rs.next()) {
                GiocatoreModel giocatoreModel = new GiocatoreModel();
                giocatoreModel.setIdGiocatore(rs.getInt("id"));
                giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
                giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
                giocatoreModelSet.add(giocatoreModel);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
        return giocatoreModelSet;
    }

    public TifoseriaModel getTifoseriaSquadraBySquadra(int idSquadra) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        TifoseriaModel tifoseriaModel = null;
        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            st = con.createStatement();
            tifoseriaModel = new TifoseriaModel();

            String query = "select * from tifoseria where id_squadra = " + idSquadra;
            rs = st.executeQuery(query);
            if (rs.next()) {
                tifoseriaModel.setIdTifoseria(rs.getInt("id"));
                tifoseriaModel.setNomeTifoseria(rs.getString("nome_tifoseria"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
        return tifoseriaModel;

    }
}