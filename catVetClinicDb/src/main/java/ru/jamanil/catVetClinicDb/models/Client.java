package ru.jamanil.catVetClinicDb.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    private String name;

    @Column(name = "dob")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date of birth must be in the past")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    @NotEmpty(message = "Phone number shouldn't be empty")
    @Pattern(regexp = "^[+]\\d[(]\\d{3}[)]\\d{3}-\\d{2}-\\d{2}$", message = "Input phone number in +7(999)123-45-67 format")
    private String phone;

    @Column(name = "created_at")
    @NotNull
    private Date createdAt;

    @Column(name = "updated_at")
    @NotNull
    private Date updatedAt;

    @Column(name = "created_by")
    @NotNull
    @NotEmpty
    private String createdBy;

    @Column(name = "updated_by")
    @NotNull
    @NotEmpty
    private String updatedBy;

    @OneToMany(mappedBy = "owner")
    private List<Cat> catList;

    public void addCats(Cat ... cats) {
        if (this.catList == null)
            this.catList = new ArrayList<>();
        catList.addAll(Arrays.asList(cats));
    }
}
