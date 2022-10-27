package ru.jamanil.catVetClinicDb.security.staff.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.jamanil.catVetClinicDb.security.staff.Staff;

import java.util.Optional;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@Repository
@RestResource(exported = false)
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Optional<Staff> findByName(String name);
    Optional<Staff> findByUsername(String username);
}
