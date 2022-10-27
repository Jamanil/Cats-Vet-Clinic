package ru.jamanil.catVetClinicDb.security.staff.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jamanil.catVetClinicDb.security.staff.Staff;
import ru.jamanil.catVetClinicDb.security.staff.dto.StaffDto;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Component
public class StaffToDtoConverter {
    private static ModelMapper modelMapper;

    @Autowired
    public StaffToDtoConverter(ModelMapper modelMapper) {
        StaffToDtoConverter.modelMapper = modelMapper;
    }

    public static StaffDto convertStaffToDto(Staff staff) {
        return modelMapper.map(staff, StaffDto.class);
    }

    public static Staff convertDtoToStaff(StaffDto staffDto) {
        return modelMapper.map(staffDto, Staff.class);
    }
}
