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

@Repository
@Profile(Costanti.TORNEO_DAO_JDBC_STATEMENT)

public class GiocatoreDaoImplJDBCStatement implements IGiocatoreDao {

    @Autowired
    ConfigurazioneDB configurazioneDB;

    @Override
    public GiocatoreModel aggiornaAmmonizione(GiocatoreDTO giocatoreDTO) throws Exception {
        Connection con = configurazioneDB.init();
        return null;
    }
}
