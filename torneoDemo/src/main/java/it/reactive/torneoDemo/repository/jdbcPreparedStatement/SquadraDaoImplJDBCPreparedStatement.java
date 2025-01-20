package it.reactive.torneoDemo.repository.jdbcPreparedStatement;

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
import java.util.*;

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
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }

            // Verifica se è duplicato
            String query = "SELECT COUNT(*) FROM giocatore WHERE id_squadra = ? AND nome_cognome = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idSquadra);
            ps.setString(2, giocatoreDTO.getNomeCognome());
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new GiocatoreDuplicatoException();
            }

            // Inserisci il nuovo giocatore
            String insertQuery = "INSERT INTO giocatore (nome_cognome, id_squadra) VALUES (?, ?)";
            ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giocatoreDTO.getNomeCognome());
            ps.setInt(2, idSquadra);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            int idGiocatore = rs.getInt("id");

            // Recupera la squadra
            String selectQuerySquadra = "SELECT nome, colori_sociali FROM squadra WHERE id = ?";
            ps = con.prepareStatement(selectQuerySquadra);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();
            SquadraModel squadraModel = new SquadraModel();
            if (rs.next()) {
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
            } else {
                throw new SquadraNonPresenteException();
            }

            // Recupera i giocatori della squadra
            String selectQueryGiocatori = "SELECT g.* FROM giocatore g WHERE g.id_squadra = ?";
            ps = con.prepareStatement(selectQueryGiocatori);
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
        }
    }

    @Override
    public SquadraModel aggiungiTifoseria(int idSquadra, TifoseriaDTO tifoseriaDTO) throws SQLException {
        SquadraModel squadraModel = new SquadraModel();
        TifoseriaModel tifoseriaModel = new TifoseriaModel();

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }

            // Controlla se la tifoseria esiste
            String querySelect = "SELECT id, nome_tifoseria FROM tifoseria WHERE id_squadra = ?";
            ps = con.prepareStatement(querySelect);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Se la tifoseria esiste, aggiorna il nome
                int idTifoseria = rs.getInt("id");
                String queryUpdate = "UPDATE tifoseria SET nome_tifoseria = ? WHERE id_squadra = ?";
                ps = con.prepareStatement(queryUpdate);
                ps.setString(1, tifoseriaDTO.getNomeTifoseria());
                ps.setInt(2, idSquadra);
                ps.executeUpdate();
                tifoseriaModel.setIdTifoseria(idTifoseria);
                tifoseriaModel.setNomeTifoseria(tifoseriaDTO.getNomeTifoseria());
            } else {
                // Inserisce una nuova tifoseria
                String queryInsert = "INSERT INTO tifoseria (nome_tifoseria, id_squadra) VALUES (?, ?)";
                ps = con.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tifoseriaDTO.getNomeTifoseria());
                ps.setInt(2, idSquadra);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    tifoseriaModel.setIdTifoseria(rs.getInt("id"));
                    tifoseriaModel.setNomeTifoseria(tifoseriaDTO.getNomeTifoseria());
                }
            }

            // Recupera i dati della squadra
            String querySquadra = "SELECT nome, colori_sociali FROM squadra WHERE id = ?";
            ps = con.prepareStatement(querySquadra);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();
            if (rs.next()) {
                squadraModel.setIdSquadra(idSquadra);
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                squadraModel.setTifoseria(tifoseriaModel);
            }
            return squadraModel;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int numeroRiga = 0;
        int idSquadra = 0;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String insertQuery = "INSERT INTO squadra (nome, colori_sociali) VALUES (?, ?)";
            ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, squadraDTO.getNome());
            ps.setString(2, squadraDTO.getColoriSociali());
            numeroRiga = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idSquadra = rs.getInt("id");
            }

        } catch (SQLException e) {
            throw new SquadraDuplicataException();
        }
        if (con != null) {
            DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
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
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());

            // Rimuovi i giocatori e la tifoseria
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
        }
        if (con != null) {
            DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
        }
    }

    @Override
    public List<SquadraModel> ricercaSquadre(boolean conGiocatori) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<SquadraModel> squadraModelList = new ArrayList<>();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String query = "SELECT s.id, nome, colori_sociali, t.nome_tifoseria FROM squadra s JOIN tifoseria t ON s.id = t.id_squadra";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idSquadra = rs.getInt("id");
                SquadraModel squadraModel = new SquadraModel();
                squadraModel.setIdSquadra(idSquadra);
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                squadraModel.setTifoseria(getTifoseriaSquadraBySquadra(idSquadra));

                // Se true, aggiungo giocatori alla squadra
                if (conGiocatori) {
                    squadraModel.setGiocatori(getGiocatoriBySquadra(idSquadra));
                }

                squadraModelList.add(squadraModel);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (con != null) {
            DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
        }
        return squadraModelList;
    }

    private Set<GiocatoreModel> getGiocatoriBySquadra(int idSquadra) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Set<GiocatoreModel> giocatoreModelSet = new HashSet<>();

        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            String query = "SELECT g.id, g.nome_cognome FROM giocatore as g JOIN squadra as sq ON g.id_squadra = sq.id WHERE g.id_squadra = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();
            while (rs.next()) {
                GiocatoreModel giocatoreModel = new GiocatoreModel();
                giocatoreModel.setIdGiocatore(rs.getInt("id"));
                giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
                giocatoreModelSet.add(giocatoreModel);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (con != null) {
            DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
        }

        return giocatoreModelSet;
    }

    public TifoseriaModel getTifoseriaSquadraBySquadra(int idSquadra) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        TifoseriaModel tifoseriaModel = null;
        try {
            con = DataSourceUtils.getConnection(((DataSourceTransactionManager) transactionManager).getDataSource());
            tifoseriaModel = new TifoseriaModel();

            String query = "SELECT * FROM tifoseria WHERE id_squadra = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idSquadra);
            rs = ps.executeQuery();
            if (rs.next()) {
                tifoseriaModel.setIdTifoseria(rs.getInt("id"));
                tifoseriaModel.setNomeTifoseria(rs.getString("nome_tifoseria"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            if (con != null) {
                DataSourceUtils.releaseConnection(con, ((DataSourceTransactionManager) transactionManager).getDataSource());
            }
        return tifoseriaModel;
    }
}