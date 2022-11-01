package ru.jamanil.catVetClinicDb.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Entity
@Table(name = "medical_history")
@Getter
@Setter
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "Medical history cannot exist without a cat")
    @ManyToOne
    @JoinColumn(name = "cat", referencedColumnName = "id")
    private Cat cat;

    @Column(name = "date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "symptoms")
    @NotNull
    private String symptoms;

    @Column(name = "diagnosis")
    @NotNull
    private String diagnosis;

    @Column(name = "therapy")
    @NotNull
    private String therapy;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    public void setCat(Cat cat) {
        this.cat = cat;
        cat.addMedicalHistoryOrder(this);
    }
}
