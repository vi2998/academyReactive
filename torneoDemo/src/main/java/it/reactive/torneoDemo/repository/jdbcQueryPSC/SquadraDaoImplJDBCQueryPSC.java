package it.reactive.torneoDemo.repository.jdbcQueryPSC;

import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.dto.SquadraDTO;
import it.reactive.torneoDemo.dto.TifoseriaDTO;
import it.reactive.torneoDemo.exception.GiocatoreDuplicatoException;
import it.reactive.torneoDemo.exception.GiocatoreNonPresenteException;
import it.reactive.torneoDemo.exception.SquadraNonPresenteException;
import it.reactive.torneoDemo.exception.TifoseriaNonPresenteException;
import it.reactive.torneoDemo.mapper.GiocatoreMapper;
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

    @Override
    public SquadraModel salvaSquadra(SquadraDTO squadraDTO) throws SQLException {

        return null;

    }

    @Override
    public SquadraModel aggiungiTifoseria(int id, TifoseriaDTO tifoseriaDTO) throws SQLException {

        return null;
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
    public List<SquadraModel> ricercaSquadre(boolean ricercaGiocatori) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public void rimuoviSquadra(int id) throws SQLException {

        int rowsUpdatedTifoseria = jdbcTemplate.update("delete from tifoseria where id_squadra = ?", id);
        int rowsUpdatedGiocatori = jdbcTemplate.update("delete from giocatore where id_squadra = ?", id);
        int rowUpdatedSquadraTorneo = jdbcTemplate.update("delete from squadra_torneo where id_squadra = ?", id);
        int rowsUpdatedSquadra = jdbcTemplate.update("delete from squadra where id = ?", id);
        if (rowsUpdatedSquadra == 0) {
            throw new SquadraNonPresenteException();
        } else {
            throw new SQLException();
        }
    }
}
