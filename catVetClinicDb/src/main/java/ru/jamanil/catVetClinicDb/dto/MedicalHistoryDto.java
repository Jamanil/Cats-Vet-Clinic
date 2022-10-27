package ru.jamanil.catVetClinicDb.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.jamanil.catVetClinicDb.models.Cat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Getter
@Setter
public class MedicalHistoryDto {
    private int id;

    private int catId;

    private Cat cat;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "symptoms")
    @NotNull(message = "Symptoms shouldn't be empty")
    private String symptoms;

    @Column(name = "diagnosis")
    @NotNull(message = "Diagnosis shouldn't be empty")
    private String diagnosis;

    @Column(name = "therapy")
    @NotNull(message = "Therapy shouldn't be empty")
    private String therapy;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
