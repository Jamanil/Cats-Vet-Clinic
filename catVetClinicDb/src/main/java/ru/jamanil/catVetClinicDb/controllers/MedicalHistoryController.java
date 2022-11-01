package ru.jamanil.catVetClinicDb.controllers;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jamanil.catVetClinicDb.dto.MedicalHistoryDto;
import ru.jamanil.catVetClinicDb.models.Cat;
import ru.jamanil.catVetClinicDb.models.MedicalHistory;
import ru.jamanil.catVetClinicDb.security.staff.utils.AuthenticationGetter;
import ru.jamanil.catVetClinicDb.services.CatService;
import ru.jamanil.catVetClinicDb.services.MedicalHistoryService;
import ru.jamanil.catVetClinicDb.utils.MedicalHistoryToDtoConverter;

import java.util.Optional;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Controller
@RequestMapping("/medical_history")
@RequiredArgsConstructor
public class MedicalHistoryController {
    private final MedicalHistoryService medicalHistoryService;
    private final CatService catService;

    @ModelAttribute("role")
    private String addRole() {
        return AuthenticationGetter.getStaffRole();
    }

    @GetMapping("/{id}")
    private String showMedicalHistoryOrder(@PathVariable("id") long id, Model model) {
        Optional<MedicalHistory> medicalHistoryOptional = medicalHistoryService.findById(id);
        medicalHistoryOptional.ifPresent(medicalHistory -> model.addAttribute("medical_history",
                MedicalHistoryToDtoConverter.convertMedicalHistoryToDto(medicalHistory)));
        return "medical_history/order";
    }

    @GetMapping("/new/{catId}")
    private String showMedicalHistoryOrderAddForm(@PathVariable("catId") long catId, Model model) {
        MedicalHistoryDto medicalHistoryDto = new MedicalHistoryDto();
        medicalHistoryDto.setCatId(catId);
        model.addAttribute("medical_history", medicalHistoryDto);
        return "medical_history/new";
    }

    @PostMapping
    private String createMedicalHistoryOrder(@ModelAttribute("medical_history") MedicalHistoryDto medicalHistoryDto,
                                             BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return "medical_history/new";
        } else {
            fillCatByCatIdField(medicalHistoryDto);
            MedicalHistory medicalHistory = MedicalHistoryToDtoConverter.convertDtoToMedicalHistory(medicalHistoryDto);
            medicalHistoryService.save(medicalHistory);
            return "redirect:/cat/" + medicalHistoryDto.getCatId();
        }
    }

    @GetMapping("/{id}/edit")
    private String showMedicalHistoryOrderEditForm(@PathVariable("id") long id, Model model) {
        Optional<MedicalHistory> medicalHistoryOptional = medicalHistoryService.findById(id);
        medicalHistoryOptional.ifPresent(medicalHistory -> model.addAttribute("medical_history",
                MedicalHistoryToDtoConverter.convertMedicalHistoryToDto(medicalHistory)));
        return "medical_history/edit";
    }

    @PatchMapping("/{id}")
    private String editMedicalHistoryOrder(@PathVariable("id") long id,
                                           @ModelAttribute("medical_history") MedicalHistoryDto medicalHistoryDto,
                                           BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return "medical_history/edit";
        } else {
            try {
                fillCatByCatIdField(medicalHistoryDto);
                MedicalHistory updatedMedicalHistory = MedicalHistoryToDtoConverter
                        .convertDtoToMedicalHistory(medicalHistoryDto);
                medicalHistoryService.update(updatedMedicalHistory, id);
                return "redirect:/cat/" + medicalHistoryDto.getCatId();
            } catch (DataIntegrityViolationException e) {
                bindingResult.rejectValue("name", "", e.getCause().getCause().getMessage());
                return "medical_history/edit";
            }
        }
    }

    @DeleteMapping("/{id}")
    private String deleteMedicalHistoryOrder(@PathVariable("id") long id) throws NotFoundException {
        Optional<MedicalHistory> optionalMedicalHistory = medicalHistoryService.findById(id);
        if (optionalMedicalHistory.isEmpty()) {
            throw new NotFoundException("Medical history order not founded by id");
        } else {
            long catId = optionalMedicalHistory.get().getCat().getId();
            medicalHistoryService.delete(id);
            return "redirect:/cat/" + catId;
        }
    }

    private void fillCatByCatIdField(MedicalHistoryDto medicalHistoryDto) throws NotFoundException {
        Optional<Cat> optionalCat = catService.findById(medicalHistoryDto.getCatId());
        if (optionalCat.isEmpty()) {
            throw new NotFoundException("Cat not founded by id");
        } else {
            medicalHistoryDto.setCat(optionalCat.get());
        }
    }
}
