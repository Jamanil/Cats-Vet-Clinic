package ru.jamanil.catVetClinicDb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.jamanil.catVetClinicDb.models.Cat;
import ru.jamanil.catVetClinicDb.models.Client;

import java.util.List;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Repository
@RestResource(exported = false)
public interface CatRepository extends JpaRepository<Cat, Integer> {
    List<Cat> findAllByOwner(Client owner);
}
