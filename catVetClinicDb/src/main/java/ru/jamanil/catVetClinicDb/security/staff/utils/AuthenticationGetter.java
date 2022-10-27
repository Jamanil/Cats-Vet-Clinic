package ru.jamanil.catVetClinicDb.security.staff.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
public class AuthenticationGetter {

    public static String getStaffName() {
        return getStaffDetails().staff().getName();
    }

    public static String getStaffRole() {
        return getStaffDetails().getAuthorities().stream().findFirst().map(Objects::toString).orElse("ANONYMOUS");
    }

    private static StaffDetails getStaffDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (StaffDetails) authentication.getPrincipal();
    }
}
