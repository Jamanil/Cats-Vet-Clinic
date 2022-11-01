package ru.jamanil.catVetClinicDb.security.staff.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.catVetClinicDb.security.staff.Staff;
import ru.jamanil.catVetClinicDb.security.staff.repositories.StaffRepository;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Staff staff) {
        String encodedPassword = passwordEncoder.encode(staff.getPassword());
        staff.setPassword(encodedPassword);
        staff.setRole("ROLE_NURSE");
        staff.setEnabled(true);
        staffRepository.save(staff);
    }
}
