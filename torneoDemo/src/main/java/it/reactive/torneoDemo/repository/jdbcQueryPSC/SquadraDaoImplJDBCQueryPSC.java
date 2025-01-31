package it.reactive.torneoDemo.repository.jdbcQueryPSC;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.exception.*;
import it.reactive.torneoDemo.mapper.GiocatoreMapper;
import it.reactive.torneoDemo.mapper.TifoseriaMapper;
import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TifoseriaModel;
import it.reactive.torneoDemo.repository.dao.ISquadraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_SPRING_JDBC_QUERY_PSC;

@Repository
@Profile(TORNEO_DAO_SPRING_JDBC_QUERY_PSC)
public class SquadraDaoImplJDBCQueryPSC implements ISquadraDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GiocatoreMapper giocatoreMapper;

    @Autowired
    TifoseriaMapper tifoseriaMapper;

    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException {

        // controllo se è duplicata
        PreparedStatementCreator pscSelectCount = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM squadra WHERE nome = ? AND colori_sociali = ?");
            ps.setString(1, squadraDTO.getNome());
            ps.setString(2, squadraDTO.getColoriSociali());
            return ps;
        };
        ResultSetExtractor<SquadraModel> rseCount = rs -> {

            if (rs.next() && rs.getInt(1) > 0) {
                throw new SquadraDuplicataException();
            }
            return null;
        };

        jdbcTemplate.query(pscSelectCount, rseCount);

        // inserisco
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pscInsert = con -> {
            String insertQueryGiocatore = "INSERT INTO squadra (nome, colori_sociali) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQueryGiocatore, new String[]{"id"});
            ps.setString(1, squadraDTO.getNome());
            ps.setString(2, squadraDTO.getColoriSociali());
            return ps;

        };
        jdbcTemplate.update(pscInsert, keyHolder);

        int idSquadra = (int) keyHolder.getKey();

        // recupero la squadra
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM squadra s WHERE s.id = ?");
            ps.setInt(1, idSquadra);
            return ps;
        };
        ResultSetExtractor<SquadraModel> rse = rs -> {
            SquadraModel squadraModel = new SquadraModel();
            if (rs.next()) {
                squadraModel.setIdSquadra(idSquadra);
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
            } else {
                throw new SquadraNonPresenteException();
            }
            return squadraModel;
        };
        SquadraModel squadraModel = jdbcTemplate.query(psc, rse);
        return squadraModel;


    }

    @Override
    public SquadraModel aggiungiTifoseria(int idSquadra, TifoseriaDTO tifoseriaDTO) throws SQLException {

        PreparedStatementCreator pscSelectCount = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM tifoseria WHERE id_squadra = ? AND nome_tifoseria = ?");
            ps.setInt(1, idSquadra);
            ps.setString(2, tifoseriaDTO.getNomeTifoseria());
            return ps;
        };
        ResultSetExtractor<SquadraModel> rseCount = rs -> {

            if (rs.next() && rs.getInt(1) > 0) {
                throw new TifoseriaDuplicataException();
            }
            return null;
        };

        jdbcTemplate.query(pscSelectCount, rseCount);

        PreparedStatementCreator pscInsert = con -> {
            String insertQueryGiocatore = "INSERT INTO tifoseria (nome_tifoseria, id_squadra) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQueryGiocatore, new String[]{"id"});
            ps.setString(1, tifoseriaDTO.getNomeTifoseria());
            ps.setInt(2, idSquadra);
            return ps;

        };
        jdbcTemplate.update(pscInsert);

        // recupero la squadra con tifoseria
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT s.id, s.nome, s.colori_sociali, t.id AS tifoseria_id, t.nome_tifoseria FROM squadra s LEFT JOIN tifoseria t ON s.id = t.id_squadra WHERE s.id = ?");
            ps.setInt(1, idSquadra);
            return ps;
        };
        ResultSetExtractor<SquadraModel> rse = rs -> {
            SquadraModel squadraModel = new SquadraModel();
            TifoseriaModel tifoseriaModel = new TifoseriaModel();
            if (rs.next()) {
                squadraModel.setIdSquadra(rs.getInt("id"));
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                tifoseriaModel.setIdTifoseria(rs.getInt("tifoseria_id"));
                tifoseriaModel.setNomeTifoseria(rs.getString("nome_tifoseria"));
                squadraModel.setTifoseria(tifoseriaModel);
            } else {
                throw new SquadraNonPresenteException();
            }
            return squadraModel;
        };
        SquadraModel squadraModel = jdbcTemplate.query(psc, rse);
        return squadraModel;
    }

    @Override
    public SquadraModel aggiungiGiocatore(int idSquadra, GiocatoreDTO giocatoreDTO) throws SQLException {


        PreparedStatementCreator pscSelectCount = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM giocatore WHERE id_squadra = ? AND nome_cognome = ?");
            ps.setInt(1, idSquadra);
            ps.setString(2, giocatoreDTO.getNomeCognome());
            return ps;
        };
        ResultSetExtractor<SquadraModel> rseCount = rs -> {

            if (rs.next() && rs.getInt(1) > 0) {
                throw new GiocatoreDuplicatoException();
            }
            return null;
        };

        jdbcTemplate.query(pscSelectCount, rseCount);


        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pscInsert = con -> {
            String insertQueryGiocatore = "INSERT INTO giocatore (nome_cognome, id_squadra) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQueryGiocatore, new String[]{"id"});
            ps.setString(1, giocatoreDTO.getNomeCognome());
            ps.setInt(2, idSquadra);
            return ps;

        };
        jdbcTemplate.update(pscInsert, keyHolder);  // aggiunto giocatore alla squadra
        int idGiocatore = (int) keyHolder.getKey();

        GiocatoreModel giocatoreModel = giocatoreMapper.fromDtoToModel(giocatoreDTO);
        giocatoreModel.setIdGiocatore(idGiocatore);

        // recupero la squadra
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT s.nome, s.colori_sociali, t.id AS tifoseria_id, t.nome_tifoseria FROM squadra s LEFT JOIN tifoseria t ON s.id = t.id_squadra WHERE s.id = ?");
            ps.setInt(1, idSquadra);
            return ps;
        };
        ResultSetExtractor<SquadraModel> rse = rs -> {
            SquadraModel squadraModel = new SquadraModel();
            if (rs.next()) {
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                Set<GiocatoreModel> giocatoriGiaPresenti = new HashSet<>();
                giocatoriGiaPresenti.add(giocatoreModel);
                squadraModel.setGiocatori(giocatoriGiaPresenti); // setto giocatore alla squadraModel
            } else {
                throw new SquadraNonPresenteException();
            }
            return squadraModel;
        };
        SquadraModel squadraModel = jdbcTemplate.query(psc, rse);
        return squadraModel;
    }


    @Override
    public void rimuoviSquadra(int id) throws SQLException {

        int rowsUpdatedTifoseria = jdbcTemplate.update("delete from tifoseria where id_squadra = ?", id);
        int rowsUpdatedGiocatori = jdbcTemplate.update("delete from giocatore where id_squadra = ?", id);
        int rowUpdatedSquadraTorneo = jdbcTemplate.update("delete from squadra_torneo where id_squadra = ?", id);

        int rowsUpdatedSquadra = jdbcTemplate.update("delete from squadra where id = ?", id);
        if (rowsUpdatedSquadra == 0) {
            throw new SquadraNonPresenteException();
        }
    }


    @Override
    public List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori) throws SQLException {

        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT s.id, nome, colori_sociali, t.nome_tifoseria FROM squadra s LEFT JOIN tifoseria t ON s.id = t.id_squadra");
            return ps;
        };

        ResultSetExtractor<List<SquadraModel>> rse = rs -> {

            List<SquadraModel> squadraModelList = new ArrayList<>();

            while (rs.next()) {
                int idSquadra = rs.getInt("id");
                SquadraModel squadraModel = new SquadraModel();
                squadraModel.setIdSquadra(idSquadra);
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                squadraModel.setTifoseria(getTifoseriaSquadraBySquadra(idSquadra));

                if (ricercaGiocatori) {
                    squadraModel.setGiocatori(getGiocatoriBySquadra(idSquadra));
                }

                squadraModelList.add(squadraModel);
            }

            return squadraModelList;
        };

        List<SquadraModel> squadraModelList = jdbcTemplate.query(psc, rse);
        return squadraModelList;
    }

public Set<GiocatoreModel> getGiocatoriBySquadra(int idSquadra) throws SQLException {

    PreparedStatementCreator psc = con -> {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM giocatore WHERE id_squadra = ?");
        ps.setInt(1, idSquadra);
        return ps;
    };

    ResultSetExtractor<Set<GiocatoreModel>> rse = rs -> {
        Set<GiocatoreModel> giocatoreModelSet = new HashSet<>();
        while (rs.next()) {
            GiocatoreModel giocatoreModel = new GiocatoreModel();
            giocatoreModel.setIdGiocatore(rs.getInt("id"));
            giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
            giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
            giocatoreModelSet.add(giocatoreModel);
        }
        return giocatoreModelSet;
    };


    Set<GiocatoreModel> giocatoreModelSet = jdbcTemplate.query(psc, rse);
    return giocatoreModelSet;
}

public TifoseriaModel getTifoseriaSquadraBySquadra(int idSquadra) throws SQLException {

    PreparedStatementCreator psc = con -> {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM tifoseria WHERE id_squadra = ?");
        ps.setInt(1, idSquadra);
        return ps;
    };
    ResultSetExtractor<TifoseriaModel> rse = rs -> {
        TifoseriaModel tifoseriaModel = new TifoseriaModel();
        if (rs.next()) {
            tifoseriaModel.setIdTifoseria(rs.getInt("id"));
            tifoseriaModel.setNomeTifoseria(rs.getString("nome_tifoseria"));
        }
        return tifoseriaModel;
    };

    TifoseriaModel tifoseriaModel = jdbcTemplate.query(psc, rse);

    return tifoseriaModel;
}
}

