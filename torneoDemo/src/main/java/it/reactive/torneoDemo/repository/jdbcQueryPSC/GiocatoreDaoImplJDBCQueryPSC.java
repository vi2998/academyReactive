package it.reactive.torneoDemo.repository.jdbcQueryPSC;

import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_SPRING_JDBC_QUERY_PSC;


@Repository
@Profile(TORNEO_DAO_SPRING_JDBC_QUERY_PSC)
public class GiocatoreDaoImplJDBCQueryPSC implements IGiocatoreDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public GiocatoreModel aggiornaAmmonizione(Integer idGiocatore) throws SQLException {
        // PreparedStatementCreator
        PreparedStatementCreator pscSelect = con -> {
            String query = "SELECT id, nome_cognome, numero_ammonizioni FROM giocatore WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idGiocatore);
            return ps;
        };

        // ResultSetExtractor per estrarre i dati del giocatore
        ResultSetExtractor<GiocatoreModel> rse = rs -> {
            if (rs.next()) {
                GiocatoreModel giocatore = new GiocatoreModel();
                giocatore.setIdGiocatore(rs.getInt("id"));
                giocatore.setNomeCognome(rs.getString("nome_cognome"));
                giocatore.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
                return giocatore;
            } else {
                throw new SQLException("Giocatore non trovato con ID: " + idGiocatore);
            }
        };

        // SELECT per ottenere il giocatore
        GiocatoreModel giocatoreModel = jdbcTemplate.query(pscSelect, rse);

        int nuoveAmmonizioni = giocatoreModel.getNumeroAmmonizioni() + 1;
        giocatoreModel.setNumeroAmmonizioni(nuoveAmmonizioni);

        // PreparedStatementCreator per l'UPDATE
        PreparedStatementCreator pscUpdate = con -> {
            String query = "UPDATE giocatore SET numero_ammonizioni = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, nuoveAmmonizioni);
            ps.setInt(2, idGiocatore);
            return ps;
        };

        // Eseguo l'UPDATE
        int rowsUpdated = jdbcTemplate.update(pscUpdate);
        if (rowsUpdated != 1) {
            throw new SQLException("Errore nell'aggiornamento del numero di ammonizioni per il giocatore con ID: " + idGiocatore);
        }

        return giocatoreModel;
    }
}
