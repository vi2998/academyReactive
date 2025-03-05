package it.reactive.academy.playwithspringbatch.config.reader;

import it.reactive.academy.playwithspringbatch.config.dto.Persona;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static it.reactive.academy.playwithspringbatch.config.ConfigJobJdbcReader.JDBC_READER;
import static it.reactive.academy.playwithspringbatch.config.datasource.ConfigurazioniDatasource.DATA_SOURCE_CLIENTI;

@Component(JDBC_READER)
public class JdbcReaderPersona extends JdbcCursorItemReader<Persona> {
    JdbcReaderPersona(@Qualifier(DATA_SOURCE_CLIENTI) DataSource dataSource){
        setDataSource(dataSource);
        setSql("select * from persona");
        RowMapper<Persona> rowMapper = new RowMapper<Persona>() {
            @Override
            public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
                Persona persona = new Persona();
                persona.setCognome(rs.getString("cognome"));
                persona.setNome(rs.getString("nome"));
                persona.setEta(rs.getInt("eta"));
                return persona;
            }
        };
                setRowMapper(rowMapper);
    }
}
