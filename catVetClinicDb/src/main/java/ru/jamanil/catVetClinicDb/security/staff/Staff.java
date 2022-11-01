package ru.jamanil.catVetClinicDb.security.staff;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */

@Entity
@Table(name = "staff")
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    private String name;

    @Column(name = "username")
    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 3, max = 30, message = "Username should be between 5 and 30 characters")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;

    @Column(name = "dob")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date of birth must be in the past")
    private Date dob;

    @Column(name = "role")
    @NotEmpty
    private String role;

    @Column(name = "post")
    @NotEmpty(message = "Job title shouldn't be empty")
    @Size(min = 2, max = 60, message = "Job title should be between 2 and 60 characters")
    private String post;

    @Column(name = "enabled")
    private boolean enabled;
}
