package ru.jamanil.catVetClinicDb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.jamanil.catVetClinicDb.models.Cat;
import ru.jamanil.catVetClinicDb.models.MedicalHistory;

import java.util.List;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Repository()
@RestResource(exported = false)
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
    List<MedicalHistory> findAllByCat(Cat cat);
}

