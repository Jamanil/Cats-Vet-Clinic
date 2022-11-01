package ru.jamanil.catVetClinicDb.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jamanil.catVetClinicDb.dto.MedicalHistoryDto;
import ru.jamanil.catVetClinicDb.models.MedicalHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Component
public class MedicalHistoryToDtoConverter {
    private static ModelMapper modelMapper;

    @Autowired
    public MedicalHistoryToDtoConverter(ModelMapper modelMapper) {
        MedicalHistoryToDtoConverter.modelMapper = modelMapper;
    }

    public static MedicalHistoryDto convertMedicalHistoryToDto(MedicalHistory medicalHistory) {
        MedicalHistoryDto medicalHistoryDto = modelMapper.map(medicalHistory, MedicalHistoryDto.class);
        medicalHistoryDto.setCatId(medicalHistory.getCat().getId());
        return medicalHistoryDto;
    }

    public static MedicalHistory convertDtoToMedicalHistory(MedicalHistoryDto medicalHistoryDto) {
        MedicalHistory medicalHistory = modelMapper.map(medicalHistoryDto, MedicalHistory.class);
        medicalHistory.setCat(medicalHistoryDto.getCat());
        return medicalHistory;
    }

    public static List<MedicalHistoryDto> convertMedicalHistoryListToDto(List<MedicalHistory> medicalHistoryList) {
        List<MedicalHistoryDto> medicalHistoryDtoList = new ArrayList<>();
        for (MedicalHistory medicalHistory : medicalHistoryList) {
            medicalHistoryDtoList.add(convertMedicalHistoryToDto(medicalHistory));
        }
        return medicalHistoryDtoList;
    }
}
