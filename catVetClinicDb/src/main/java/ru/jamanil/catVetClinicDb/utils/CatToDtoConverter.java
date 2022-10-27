package ru.jamanil.catVetClinicDb.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jamanil.catVetClinicDb.dto.CatDto;
import ru.jamanil.catVetClinicDb.models.Cat;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Component
public class CatToDtoConverter {
    private static ModelMapper modelMapper;

    @Autowired
    public CatToDtoConverter(ModelMapper modelMapper) {
        CatToDtoConverter.modelMapper = modelMapper;
    }

    public static CatDto convertCatToDto(Cat cat) {
        CatDto catDto = modelMapper.map(cat, CatDto.class);
        catDto.setClientId(cat.getOwner().getId());
        return catDto;
    }

    public static Cat convertDtoToCat(CatDto catDto) {
        Cat cat = modelMapper.map(catDto, Cat.class);
        cat.setOwner(catDto.getOwner());
        return cat;
    }
}
