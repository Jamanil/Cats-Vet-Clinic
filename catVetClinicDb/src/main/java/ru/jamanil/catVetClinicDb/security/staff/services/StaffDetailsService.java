package ru.jamanil.catVetClinicDb.security.staff.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.jamanil.catVetClinicDb.security.staff.Staff;
import ru.jamanil.catVetClinicDb.security.staff.utils.StaffDetails;
import ru.jamanil.catVetClinicDb.security.staff.repositories.StaffRepository;

import java.util.Optional;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@Service
public class StaffDetailsService implements UserDetailsService {
    private final StaffRepository staffRepository;

    @Autowired
    public StaffDetailsService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Staff> staff = staffRepository.findByUsername(username);
        if (staff.isEmpty())
            throw new UsernameNotFoundException("User not founded");
        return new StaffDetails(staff.get());
    }
}
