package ru.jamanil.catVetClinicDb.security.staff.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.catVetClinicDb.security.staff.Staff;
import ru.jamanil.catVetClinicDb.security.staff.repositories.StaffRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@Service
@Transactional(readOnly = true)
public class StaffService {
    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Optional<Staff> findById(int id) {
        return staffRepository.findById(id);
    }

    public Optional<Staff> findByName(String name) {
        return staffRepository.findByName(name);
    }

    public Optional<Staff> findByUsername(String username) {
        return staffRepository.findByUsername(username);
    }

    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Transactional
    public void save(Staff staff) {
        staffRepository.save(staff);
    }

    @Transactional
    public void update(Staff updatedStaff, int id) {
        Optional<Staff> staffFromDb = findById(id);

        if(staffFromDb.isPresent()) {
            staffFromDb.get().setName(updatedStaff.getName());
            staffFromDb.get().setUsername(updatedStaff.getUsername());
            staffFromDb.get().setDob(updatedStaff.getDob());
            staffFromDb.get().setPost(updatedStaff.getPost());
            staffFromDb.get().setRole(updatedStaff.getRole());
            staffFromDb.get().setEnabled(updatedStaff.isEnabled());
            save(staffFromDb.get());
        }
    }

    @Transactional
    public void delete(int id) {
        staffRepository.deleteById(id);
    }
}
