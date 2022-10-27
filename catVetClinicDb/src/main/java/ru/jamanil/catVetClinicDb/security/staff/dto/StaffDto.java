package ru.jamanil.catVetClinicDb.security.staff.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@Getter
@Setter
public class StaffDto {
    private int id;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    private String name;

    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 3, max = 30, message = "Username should be between 5 and 30 characters")
    private String username;

    // password and confirmation validate on StaffDtoValidator
    private String password;
    private String passwordConfirmation;

    @NotNull(message = "Date of birth required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date of birth must be in the past")
    private Date dob;

    private String role;

    @NotEmpty(message = "Job title shouldn't be empty")
    @Size(min = 2, max = 60, message = "Job title should be between 2 and 60 characters")
    private String post;

    private boolean enabled;
}
