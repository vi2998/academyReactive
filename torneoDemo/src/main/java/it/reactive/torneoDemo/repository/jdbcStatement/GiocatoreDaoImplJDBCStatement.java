package it.reactive.torneoDemo.repository.jdbcStatement;

import it.reactive.torneoDemo.Costanti;
import it.reactive.torneoDemo.dto.GiocatoreDTO;
import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile(Costanti.TORNEO_DAO_JDBC_STATEMENT)

public class GiocatoreDaoImplJDBCStatement implements IGiocatoreDao {

    @Override
    public GiocatoreModel create(GiocatoreDTO giocatoreDTO) {
        return null;
    }

    @Override
    public GiocatoreModel read(int id) {
        return null;
    }

    @Override
    public GiocatoreModel update(int id, GiocatoreDTO GiocatoreDTO) {
        return null;
    }

    @Override
    public GiocatoreModel delete(int id) {
        return null;
    }
}
