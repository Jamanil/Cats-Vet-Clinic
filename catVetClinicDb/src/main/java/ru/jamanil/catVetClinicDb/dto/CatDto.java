package ru.jamanil.catVetClinicDb.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.jamanil.catVetClinicDb.models.Client;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Getter
@Setter
public class CatDto {
    private int id;

    private int clientId;

    @ToString.Exclude
    private Client owner;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    private String name;

    @NotNull(message = "Date of birth shouldn't be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date of birth must be in the past")
    private Date dob;

    @NotEmpty(message = "Color shouldn't be empty")
    @Size(min = 2, max = 30, message = "Color should be between 2 and 30 characters")
    private String color;

    @NotNull(message = "Weight shouldn't be empty")
    @Min(value = 0, message = "Cat cannot have a negative weight")
    @Max(value = 30, message = "Cat cannot have a weight more then 30 kg")
    private Float weight;
}
