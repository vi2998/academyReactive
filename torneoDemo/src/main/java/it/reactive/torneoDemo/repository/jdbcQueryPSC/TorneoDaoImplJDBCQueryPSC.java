package it.reactive.torneoDemo.repository.jdbcQueryPSC;

import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.exception.TorneoDuplicatoException;
import it.reactive.torneoDemo.exception.TorneoNonTrovatoException;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_SPRING_JDBC_QUERY_PSC;

@Repository
@Profile(TORNEO_DAO_SPRING_JDBC_QUERY_PSC)
public class TorneoDaoImplJDBCQueryPSC implements ITorneoDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SquadraDaoImplJDBCQueryPSC squadraDaoImplJDBCQueryPSC;

    @Override
    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {

        PreparedStatementCreator pscSelectCount = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM torneo WHERE nome_torneo = ?");
            ps.setString(1, torneoDTO.getNomeTorneo());
            return ps;
        };
        ResultSetExtractor<TorneoModel> rseCount = rs -> {

            if (rs.next() && rs.getInt(1) > 0) {
                throw new TorneoDuplicatoException();
            }
            return null;
        };

        jdbcTemplate.query(pscSelectCount, rseCount);

        // inserisco
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator pscInsert = con -> {
            String insertQueryTorneo = "INSERT INTO torneo (nome_torneo) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(insertQueryTorneo, new String[]{"id"});
            ps.setString(1, torneoDTO.getNomeTorneo());
            return ps;
        };
        jdbcTemplate.update(pscInsert, keyHolder);

        int idTorneo = (int) keyHolder.getKey();

        // recupero il torneo
        PreparedStatementCreator psc = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM torneo WHERE id = ?");
            ps.setInt(1, idTorneo);
            return ps;
        };
        ResultSetExtractor<TorneoModel> rse = rs -> {

            TorneoModel torneoModel = new TorneoModel();

            if (rs.next()) {
                torneoModel.setIdTorneo(idTorneo);
                torneoModel.setNomeTorneo(rs.getString("nome_torneo"));
            } else {
                throw new TorneoNonTrovatoException();
            }
            return torneoModel;
        };
        TorneoModel torneoModel = jdbcTemplate.query(psc, rse);
        return torneoModel;
    }

    @Override
    public void eliminaTorneo(int id) throws SQLException {

        List<Integer> squadrePerTorneo = listaSquadrePerTorneo(id);
        for (Integer idSquadra : squadrePerTorneo) {
            int contaPresenzeSquadra = contaPresenzeSquadraTorneo(idSquadra);
            if (contaPresenzeSquadra == 1){
                squadraDaoImplJDBCQueryPSC.rimuoviSquadra(idSquadra);
            } else {
                jdbcTemplate.update("delete from squadra_torneo where id_squadra = " + idSquadra + " and id_torneo = ?", id);
            }
        }
        jdbcTemplate.update("delete from torneo where id = ?", id);

    }

    private int contaPresenzeSquadraTorneo(Integer idSquadra) {
        PreparedStatementCreator pscSelectCount = con -> {
            PreparedStatement ps = con.prepareStatement("select count(*) from squadra_torneo st where id_squadra = ?");
            ps.setInt(1, idSquadra);
            return ps;
        };
        ResultSetExtractor<Integer> rse = rs -> {
            int contaPresenze= 0;
            if (rs.next()) {
                contaPresenze = rs.getInt("count");
            }
            return contaPresenze;
        };
        int presenzaSquadra = jdbcTemplate.query(pscSelectCount, rse);
        return presenzaSquadra;
    }

    private List<Integer> listaSquadrePerTorneo(Integer idTorneo) {

        PreparedStatementCreator pscSelectCount = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT s.id FROM squadra s " +
                    "LEFT JOIN squadra_torneo st " +
                    "ON s.id = st.id_squadra " +
                    "LEFT JOIN torneo t ON t.id = st.id_torneo WHERE t.id = ?");
            ps.setInt(1, idTorneo);
            return ps;
        };
        ResultSetExtractor<List<Integer>> rse = rs -> {
            List<Integer> squadraIntList = new ArrayList<>();
            while (rs.next()) {
                squadraIntList.add(rs.getInt("id"));
            }
            return squadraIntList;
        };
        List<Integer> squadraIntList = jdbcTemplate.query(pscSelectCount, rse);
        return squadraIntList;
    }

    @Override
    public TorneoModel associaTorneoASquadra(int idTorneo, int idSquadra) throws SQLException {
        return null;
    }

    @Override
    public List<TorneoModel> cercaTorneiAndSquadre() throws SQLException {

        PreparedStatementCreator pscSelectCount = con -> {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM torneo");

            return ps;
        };
        ResultSetExtractor<List<TorneoModel>> rse = rs -> {

            List<TorneoModel> torneoModelList = new ArrayList<>();

            while (rs.next()) {
                TorneoModel torneo = new TorneoModel();
                torneo.setIdTorneo(rs.getInt("id"));
                torneo.setNomeTorneo(rs.getString("nome_torneo"));
                torneo.setSquadre(recuperaSquadreByIdTorneo(rs.getInt("id")));
                torneoModelList.add(torneo);

            }
            return torneoModelList;
        };

        List<TorneoModel> torneoModelList = jdbcTemplate.query(pscSelectCount, rse);
        return torneoModelList;
    }

    private Set<SquadraModel> recuperaSquadreByIdTorneo(int id) {
        PreparedStatementCreator pscSelectCount = con -> {
            String query = "SELECT s.nome, s.id, s.colori_sociali FROM squadra_torneo st JOIN squadra s ON s.id = st.id_squadra WHERE st.id_torneo = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            return ps;
        };
        ResultSetExtractor<Set<SquadraModel>> rse = rs -> {
            Set<SquadraModel> squadraModelSet = new HashSet<>();
            while (rs.next()) {
                SquadraModel squadraModel = new SquadraModel();
                squadraModel.setNome(rs.getString("nome"));
                squadraModel.setIdSquadra(rs.getInt("id"));
                squadraModel.setColoriSociali(rs.getString("colori_sociali"));
                squadraModel.setGiocatori(squadraDaoImplJDBCQueryPSC.getGiocatoriBySquadra(rs.getInt("id")));
                squadraModel.setTifoseria(squadraDaoImplJDBCQueryPSC.getTifoseriaSquadraBySquadra(rs.getInt("id")));
                squadraModelSet.add(squadraModel);

            }
            return squadraModelSet;
        };

        Set<SquadraModel> squadraModelSet = new HashSet<>();
        squadraModelSet = jdbcTemplate.query(pscSelectCount, rse);
        return squadraModelSet;
    }
}
