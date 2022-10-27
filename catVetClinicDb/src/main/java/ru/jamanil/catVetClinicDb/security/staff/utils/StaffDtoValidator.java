package ru.jamanil.catVetClinicDb.security.staff.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.jamanil.catVetClinicDb.security.staff.dto.StaffDto;
import ru.jamanil.catVetClinicDb.security.staff.services.StaffService;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@SuppressWarnings("NullableProblems")
@Component
public class StaffDtoValidator implements Validator {
    private final StaffService staffService;

    @Autowired
    public StaffDtoValidator(StaffService staffService) {
        this.staffService = staffService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StaffDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StaffDto staffDto = (StaffDto) target;

        if (staffService.findByName(staffDto.getName()).isPresent())
            errors.rejectValue("name", "", "An employee with this name already exists in the database");

        if(staffService.findByUsername(staffDto.getUsername()).isPresent())
            errors.rejectValue("username", "", "Username taken");

        if(staffDto.getPassword() == null)
            errors.rejectValue("password", "", "Password shouldn't be empty");

        if(staffDto.getPassword().length() < 5 || staffDto.getPassword().length() > 30)
            errors.rejectValue("password", "", "Password should be between 5 and 30 characters");

        if(staffDto.getPasswordConfirmation() == null)
            errors.rejectValue("password", "", "Password confirmation shouldn't be empty");

        if(staffDto.getPasswordConfirmation().length() < 5 || staffDto.getPasswordConfirmation().length() > 30)
            errors.rejectValue("password", "", "Password confirmation should be between 5 and 30 characters");

        if(!staffDto.getPassword().equals(staffDto.getPasswordConfirmation()))
            errors.rejectValue("password", "", "Password and confirmation do not match");
    }
}
