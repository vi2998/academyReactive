package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.Costanti;
import it.reactive.torneoDemo.configuration.ConfigurazioneDB;
import it.reactive.torneoDemo.dto.TorneoDTO;
import it.reactive.torneoDemo.exception.SquadraNonPresenteException;
import it.reactive.torneoDemo.model.SquadraModel;
import it.reactive.torneoDemo.model.TorneoModel;
import it.reactive.torneoDemo.repository.dao.ITorneoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
@Profile(Costanti.TORNEO_DAO_JDBC_STATEMENT)

public class ITorneoDaoImplJDBCStatement implements ITorneoDao {

    @Autowired
    ConfigurazioneDB configurazioneDB;

    @Override
    public TorneoModel aggiungiTorneo(TorneoDTO torneoDTO) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        String insertQuery = "INSERT INTO torneo (nome_torneo) VALUES ('" + torneoDTO.getNomeTorneo() + "')";
        int numeroRiga = st.executeUpdate(insertQuery);
        if (numeroRiga != 1) {
            System.out.println("Qualcosa è andato storto");
        }else{
            con.commit();
        }
        TorneoModel torneoModel = new TorneoModel();
        torneoModel.setNomeTorneo(torneoDTO.getNomeTorneo());
        con.close();
        return torneoModel;
    }

    @Override
    public void eliminaTorneo(int idTorneo) throws SQLException {
        Connection con = configurazioneDB.init();
        Statement st = con.createStatement();

        st.executeUpdate("DELETE FROM squadra_torneo WHERE id_torneo = " + idTorneo);
        st.executeUpdate("DELETE FROM giocatore WHERE id_squadra IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");
        st.executeUpdate("DELETE FROM tifoseria WHERE id_squadra IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");
        st.executeUpdate("DELETE FROM squadra WHERE id IN (SELECT id_squadra FROM squadra_torneo WHERE id_torneo = " + idTorneo + ")");

        int nRow = st.executeUpdate("delete from torneo where id= " + idTorneo);
        if (nRow == 0) {
            System.out.println("Qualcosa è andato storto");
        }
        con.commit();
        con.close();
    }

    @Override
    public TorneoModel update(int idTorneo, int idSquadra) {
        return null;
    }
}
