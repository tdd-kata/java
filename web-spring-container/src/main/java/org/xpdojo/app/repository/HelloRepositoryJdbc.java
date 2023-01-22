package org.xpdojo.app.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.xpdojo.app.Hello;

@Repository
public class HelloRepositoryJdbc implements HelloRepository {

    private final JdbcTemplate jdbcTemplate;

    public HelloRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Hello findHello(String name) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from hello where name = ?",
                    new Object[]{name},
                    (rs, rowNum) -> {
                        String helloName = rs.getString("name");
                        int helloCount = rs.getInt("count");
                        return new Hello(helloName, helloCount);
                    });
        } catch (EmptyResultDataAccessException ex) {
            System.err.println(ex);
            return null;
        }
    }

    @Override
    public void increaseCount(String name) {
        Hello hello = findHello(name);

        if (hello == null) {
            jdbcTemplate.update("insert into hello (name, count) values (?, ?)", name, 1);
            return;
        }

        jdbcTemplate.update("update hello set count = ? where name = ?", hello.getCount() + 1, name);
    }

}
