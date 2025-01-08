package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.Costanti;
import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
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

@Repository
@Profile(Costanti.TORNEO_DAO_JDBC_STATEMENT)

public class GiocatoreDaoImplJDBCStatement implements IGiocatoreDao {

    @Autowired
    ConfigurazioneDB configurazioneDB;

    @Override
    public GiocatoreModel aggiornaAmmonizione(Integer idGiocatore) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();
        String query = "UPDATE giocatore set numero_ammonizioni = numero_ammonizioni + 1 WHERE id = " + idGiocatore;
        int numeroRiga = st.executeUpdate(query);
        if (numeroRiga != 1){   // L'aggiornamento dovrebbe riguardare solo un record, poiché l'id del giocatore è univoco.
                                // Se non viene aggiornata alcuna riga, l'id potrebbe non esistere.
                                // Se vengono aggiornate più righe, c'è un errore logico nel database o nella query.
            System.out.println("Qualcosa è andato storto");
        }else{
            //con.commit();
        }
        query = "select g.nome_cognome,g.numero_ammonizioni, s.nome as nome_squadra from giocatore g join squadra s on G.id_squadra = s.id";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        GiocatoreModel giocatoreModel = new GiocatoreModel();
        giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
        giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
        SquadraModel squadraModel = new SquadraModel();
        squadraModel.setNome(rs.getString("nome_squadra"));
        giocatoreModel.setSquadra(squadraModel);
        con.close();
        return giocatoreModel;
    }
}
