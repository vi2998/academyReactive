package it.reactive.torneoDemo.repository.jdbcPreparedStatement;

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

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_PREPAREDSTATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_PREPAREDSTATEMENT)
public class SquadraDaoImplJDBCPreparedStatement implements ISquadraDao {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Override
    public SquadraModel aggiungiGiocatore(int idSquadra, GiocatoreDTO giocatoreDTO) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());

            // Verifica se è duplicato
            String query = "SELECT COUNT(*) FROM giocatore WHERE id_squadra = ? AND nome_cognome = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idSquadra);
            ps.setString(2, giocatoreDTO.getNomeCognome());
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new GiocatoreDuplicatoException();
            }

            String insertQueryGiocatore = "INSERT INTO giocatore (nome_cognome, id_squadra) VALUES (?, ?)";
            ps = con.prepareStatement(insertQueryGiocatore, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, giocatoreDTO.getNomeCognome());
            ps.setInt(2, idSquadra);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            int idGiocatore = rs.getInt(1);

            String selectQuerySquadraConTifoseria = "SELECT s.nome, s.colori_sociali, t.id AS tifoseria_id, t.nome_tifoseria FROM squadra s LEFT JOIN tifoseria t ON s.id = t.id_squadra WHERE s.id = ?";
            ps = con.prepareStatement(selectQuerySquadraConTifoseria);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();
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

            String selectGiocatoriSquadra = "SELECT id, nome_cognome, numero_ammonizioni FROM giocatore WHERE id_squadra = ?";
            ps = con.prepareStatement(selectGiocatoriSquadra);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();

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
            if (con != null)
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
        }
    }

    @Override
    public SquadraModel aggiungiTifoseria(int idSquadra, TifoseriaDTO tifoseriaDTO) throws SQLException {
        SquadraModel squadraModel = new SquadraModel();
        TifoseriaModel tifoseriaModel = new TifoseriaModel();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());

            // Verifica se esiste già una tifoseria
            String querySelect = "SELECT id, nome_tifoseria FROM tifoseria WHERE id_squadra = ?";
            ps = con.prepareStatement(querySelect);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idTifoseria = rs.getInt("id");
                String queryUpdateTifoseria = "UPDATE tifoseria SET nome_tifoseria = ? WHERE id_squadra = ?";
                ps = con.prepareStatement(queryUpdateTifoseria);
                ps.setString(1, tifoseriaDTO.getNomeTifoseria());
                ps.setInt(2, idSquadra);
                ps.executeUpdate();
                tifoseriaModel.setIdTifoseria(idTifoseria);
                tifoseriaModel.setNomeTifoseria(tifoseriaDTO.getNomeTifoseria());
            } else {
                String queryInsertTifoseria = "INSERT INTO tifoseria (nome_tifoseria, id_squadra) VALUES (?, ?)";
                ps = con.prepareStatement(queryInsertTifoseria, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, tifoseriaDTO.getNomeTifoseria());
                ps.setInt(2, idSquadra);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    tifoseriaModel.setIdTifoseria(rs.getInt(1));
                    tifoseriaModel.setNomeTifoseria(tifoseriaDTO.getNomeTifoseria());
                }
            }

            String querySquadra = "SELECT nome, colori_sociali FROM squadra WHERE id = ?";
            ps = con.prepareStatement(querySquadra);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();
            if (rs.next()) {
                squadraModel.setIdSquadra(idSquadra);
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
            }

            String selectGiocatori = "SELECT id, nome_cognome, numero_ammonizioni FROM giocatore WHERE id_squadra = ?";
            ps = con.prepareStatement(selectGiocatori);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();

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

            return squadraModel;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null)
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
        }
    }

    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idSquadra = 0;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String insertQuerySquadra = "INSERT INTO squadra (nome, colori_sociali) VALUES (?, ?)";
            ps = con.prepareStatement(insertQuerySquadra, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, squadraDTO.getNome());
            ps.setString(2, squadraDTO.getColoriSociali());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idSquadra = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new SquadraDuplicataException();
        } finally {
            if (con != null)
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
        }

        SquadraModel squadraModel = new SquadraModel();
        squadraModel.setIdSquadra(idSquadra);
        squadraModel.setNome(squadraDTO.getNome());
        squadraModel.setColoriSociali(squadraDTO.getColoriSociali());
        return squadraModel;
    }

    @Override
    public void rimuoviSquadra(int id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            ps = con.prepareStatement("DELETE FROM giocatore WHERE id_squadra = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            ps = con.prepareStatement("DELETE FROM tifoseria WHERE id_squadra = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            ps = con.prepareStatement("DELETE FROM squadra WHERE id = ?");
            ps.setInt(1, id);
            int nRow = ps.executeUpdate();
            if (nRow == 0) {
                throw new SquadraNonPresenteException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }


        @Override
        public List<SquadraModel> ricercaSquadre ( boolean conGiocatori) throws SQLException {
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            List<SquadraModel> squadraModelList = new ArrayList<>();

            try {
                con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());

                String querySquadraConTifoseria = "SELECT s.id, nome, colori_sociali, t.nome_tifoseria FROM squadra s LEFT JOIN tifoseria t ON s.id = t.id_squadra";
                ps = con.prepareStatement(querySquadraConTifoseria);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int idSquadra = rs.getInt("id");
                    SquadraModel squadraModel = new SquadraModel();
                    squadraModel.setIdSquadra(idSquadra);
                    squadraModel.setNome(rs.getString("nome"));
                    squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                    squadraModel.setTifoseria(getTifoseriaSquadraBySquadra(idSquadra));

                    if (conGiocatori) {
                        squadraModel.setGiocatori(getGiocatoriBySquadra(idSquadra));
                    }

                    squadraModelList.add(squadraModel);
                }

                return squadraModelList;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (con != null) {
                    DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
                }
            }
        }

        public Set<GiocatoreModel> getGiocatoriBySquadra ( int idSquadra) throws SQLException {
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            Set<GiocatoreModel> giocatoreModelSet = new HashSet<>();

            try {
                con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
                String query = "SELECT g.id, g.nome_cognome, g.numero_ammonizioni FROM giocatore g WHERE g.id_squadra = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, idSquadra);
                rs = ps.executeQuery();

                while (rs.next()) {
                    GiocatoreModel giocatoreModel = new GiocatoreModel();
                    giocatoreModel.setIdGiocatore(rs.getInt("id"));
                    giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
                    giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
                    giocatoreModelSet.add(giocatoreModel);
                }

                return giocatoreModelSet;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (con != null)
                    DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }

        public TifoseriaModel getTifoseriaSquadraBySquadra ( int idSquadra) throws SQLException {
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            TifoseriaModel tifoseriaModel = new TifoseriaModel();

            try {
                con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
                String query = "SELECT * FROM tifoseria WHERE id_squadra = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, idSquadra);
                rs = ps.executeQuery();

                if (rs.next()) {
                    tifoseriaModel.setIdTifoseria(rs.getInt("id"));
                    tifoseriaModel.setNomeTifoseria(rs.getString("nome_tifoseria"));
                }

                return tifoseriaModel;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (con != null)
                    DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        }
    }