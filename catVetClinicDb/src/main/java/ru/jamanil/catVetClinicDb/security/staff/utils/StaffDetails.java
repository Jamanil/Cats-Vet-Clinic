package ru.jamanil.catVetClinicDb.security.staff.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.jamanil.catVetClinicDb.security.staff.Staff;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
public record StaffDetails(Staff staff) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(staff.getRole()));
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return staff.isEnabled();
    }
}
