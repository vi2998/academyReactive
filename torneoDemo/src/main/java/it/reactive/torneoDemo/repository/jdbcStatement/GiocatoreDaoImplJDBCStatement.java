package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.model.GiocatoreModel;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static it.reactive.torneoDemo.Costanti.TORNEO_DAO_JDBC_STATEMENT;

@Repository
@Profile(TORNEO_DAO_JDBC_STATEMENT)

public class GiocatoreDaoImplJDBCStatement implements IGiocatoreDao {

    @Autowired
    ConfigurazioneDB configurazioneDB;

    @Override
    public GiocatoreModel aggiornaAmmonizione(Integer idGiocatore) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();
        String query = "UPDATE giocatore set numero_ammonizioni = numero_ammonizioni + 1 WHERE id = " + idGiocatore;
        int numeroRiga = st.executeUpdate(query);
        if (numeroRiga != 1) {
            System.out.println("Qualcosa è andato storto");
        } else {
            con.commit();
        }

        query = "select g.id, g.nome_cognome, g.numero_ammonizioni, s.nome as nome_squadra " +
                "from giocatore g " +
                "join squadra s on g.id_squadra = s.id " +
                "where g.id = " + idGiocatore;
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            GiocatoreModel giocatoreModel = new GiocatoreModel();
            giocatoreModel.setIdGiocatore(rs.getInt("id"));
            giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
            giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));

            SquadraModel squadraModel = new SquadraModel();
            squadraModel.setNome(rs.getString("nome_squadra"));
            giocatoreModel.setSquadra(squadraModel);

            con.close();
            return giocatoreModel;
        } else {
            // Giocatore non trovato
            con.close();
            throw new SQLException("Giocatore non trovato con ID: " + idGiocatore);
        }
    }

}
