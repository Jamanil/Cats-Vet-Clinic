package ru.jamanil.catVetClinicDb.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Entity
@Table(name = "cat")
@Getter
@Setter
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Client owner;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    private String name;

    @Column(name = "dob")
    @NotNull(message = "Date of birth shouldn't be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date of birth must be in the past")
    private Date dob;

    @Column(name = "color")
    @NotEmpty(message = "Color shouldn't be empty")
    @Size(min = 2, max = 30, message = "Color should be between 2 and 30 characters")
    private String color;

    @Column(name = "weight")
    @NotNull
    @Min(value = 0, message = "Cat cannot have a negative weight")
    @Max(value = 30, message = "Cat cannot have a weight more then 30 kg")
    private Float weight;

    @OneToMany(mappedBy = "cat")
    private List<MedicalHistory> medicalHistoryList;

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

    public void setOwner(Client owner) {
        this.owner = owner;
        owner.addCats(this);
    }

    public void addMedicalHistoryOrder(MedicalHistory ... medicalHistories) {
        if (this.medicalHistoryList == null)
            this.medicalHistoryList = new ArrayList<>();

        medicalHistoryList.addAll(Arrays.asList(medicalHistories));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
