package ru.jamanil.catVetClinicDb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.jamanil.catVetClinicDb.models.Client;

import java.util.List;

/**
 * @author Victor Datsenko
 * 27.10.2022
 */
@Component
public class ClientSearchDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientSearchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> findBySubstring(String searchBy, String substringForSearching) {
        String sql = "SELECT * FROM client WHERE " + searchBy + " ILIKE '%" + substringForSearching + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client.class));

    }

}