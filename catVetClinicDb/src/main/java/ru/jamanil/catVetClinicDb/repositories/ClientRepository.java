package ru.jamanil.catVetClinicDb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.jamanil.catVetClinicDb.models.Client;

import java.util.Optional;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Repository
@RestResource(exported = false)
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByName(String name);
    Optional<Client> findByPhone(String phone);
    Optional<Client> findByEmail(String email);
}
