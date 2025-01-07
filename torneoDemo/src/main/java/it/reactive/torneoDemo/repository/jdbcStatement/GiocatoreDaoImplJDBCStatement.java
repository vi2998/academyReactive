package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.Costanti;
import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.model.GiocatoreModel;
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
        if (numeroRiga != 1){
            System.out.println("Qualcosa è andato storto");
        }else{
            con.commit();
        }
        query = "SELECT * FROM giocatore WHERE id = " + idGiocatore;
        ResultSet rs = st.executeQuery(query);
        rs.next();
        GiocatoreModel giocatoreModel = new GiocatoreModel();
        giocatoreModel.setNomeCognome(rs.getString("nome_cognome"));
        giocatoreModel.setNumeroAmmonizioni(rs.getInt("numero_ammonizioni"));
        con.close();
        return giocatoreModel;
    }
}
