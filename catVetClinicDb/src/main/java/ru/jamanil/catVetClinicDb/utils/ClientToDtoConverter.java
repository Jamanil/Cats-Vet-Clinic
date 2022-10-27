package ru.jamanil.catVetClinicDb.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jamanil.catVetClinicDb.dto.ClientDto;
import ru.jamanil.catVetClinicDb.models.Client;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Component
public class ClientToDtoConverter {
    private static ModelMapper modelMapper;

    @Autowired
    public ClientToDtoConverter(ModelMapper modelMapper) {
        ClientToDtoConverter.modelMapper = modelMapper;
    }

    public static ClientDto convertClientToDto(Client client) {
        return modelMapper.map(client, ClientDto.class);
    }

    public static Client convertDtoToClient(ClientDto clientDto) {
        return modelMapper.map(clientDto, Client.class);
    }
}
