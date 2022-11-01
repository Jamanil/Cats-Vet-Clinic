package ru.jamanil.catVetClinicDb.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.jamanil.catVetClinicDb.dto.ClientDto;
import ru.jamanil.catVetClinicDb.services.ClientService;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@SuppressWarnings("NullableProblems")
@Component
@RequiredArgsConstructor
public class ClientDtoValidator implements Validator {
    private final ClientService clientService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ClientDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientDto clientDto = (ClientDto) target;

        if (clientService.findByName(clientDto.getName()).isPresent())
            errors.rejectValue("name", "", "A client with the same name already exists in the database");

        if(clientDto.getEmail() != null && !clientDto.getEmail().isEmpty() &&
        clientService.findByEmail(clientDto.getEmail()).isPresent())
            errors.rejectValue("email", "", "A client with the same email already exists in the database");

        if(clientService.findByPhone(clientDto.getPhone()).isPresent())
            errors.rejectValue("phone", "", "A client with the same phone already exists in the database");
    }
}
