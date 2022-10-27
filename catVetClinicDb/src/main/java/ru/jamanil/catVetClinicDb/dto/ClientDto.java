package ru.jamanil.catVetClinicDb.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Getter
@Setter
public class ClientDto {
    private int id;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    private String name;

    @NotNull(message = "Date of birth required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date of birth must be in the past")
    private Date dob;

    private String address;

    @Email(message = "Incorrect email")
    private String email;

    @NotEmpty(message = "Phone number shouldn't be empty")
    @Pattern(regexp = "^[+]\\d[(]\\d{3}[)]\\d{3}-\\d{2}-\\d{2}$", message = "Input phone number in +7(999)123-45-67 format")
    private String phone;
}
