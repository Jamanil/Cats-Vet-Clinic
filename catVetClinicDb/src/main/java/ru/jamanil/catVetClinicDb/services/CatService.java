package ru.jamanil.catVetClinicDb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.catVetClinicDb.models.Cat;
import ru.jamanil.catVetClinicDb.models.Client;
import ru.jamanil.catVetClinicDb.repositories.CatRepository;
import ru.jamanil.catVetClinicDb.security.staff.utils.AuthenticationGetter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Service
@Transactional(readOnly = true)
public class CatService {
    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public List<Cat> findAllByOwner(Client owner) {
        return catRepository.findAllByOwner(owner);
    }

    public Optional<Cat> findById(int id) {
        return catRepository.findById(id);
    }

    @Transactional
    public void save(Cat cat) {
        String staffName = AuthenticationGetter.getStaffName();
        Date date = new Date();
        cat.setCreatedAt(date);
        cat.setCreatedBy(staffName);
        cat.setUpdatedAt(date);
        cat.setUpdatedBy(staffName);
        catRepository.save(cat);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public void update(Cat updatedCat, int id) {
        Optional<Cat> catOptional = findById(id);

        if (catOptional.isPresent()) {
            Cat cat = catOptional.get();
            cat.setOwner(updatedCat.getOwner());
            cat.setName(updatedCat.getName());
            cat.setDob(updatedCat.getDob());
            cat.setColor(updatedCat.getColor());
            cat.setWeight(updatedCat.getWeight());
            cat.setUpdatedAt(new Date());
            cat.setUpdatedBy(AuthenticationGetter.getStaffName());

            catRepository.save(cat);
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        catRepository.deleteById(id);
    }
}
