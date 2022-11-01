package ru.jamanil.catVetClinicDb.dao;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ClientSearchDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Client> findBySubstring(String searchBy, String substringForSearching) {
        String sql =  String.format("SELECT * FROM client WHERE %s ILIKE %s%s%s", searchBy, "'%", substringForSearching, "%'");
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Client.class));
    }

}