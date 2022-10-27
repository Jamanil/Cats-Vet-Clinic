package ru.jamanil.catVetClinicDb.controllers;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jamanil.catVetClinicDb.dto.CatDto;
import ru.jamanil.catVetClinicDb.dto.MedicalHistoryDto;
import ru.jamanil.catVetClinicDb.models.Cat;
import ru.jamanil.catVetClinicDb.models.Client;
import ru.jamanil.catVetClinicDb.models.MedicalHistory;
import ru.jamanil.catVetClinicDb.security.staff.utils.AuthenticationGetter;
import ru.jamanil.catVetClinicDb.services.CatService;
import ru.jamanil.catVetClinicDb.services.ClientService;
import ru.jamanil.catVetClinicDb.services.MedicalHistoryService;
import ru.jamanil.catVetClinicDb.utils.CatToDtoConverter;
import ru.jamanil.catVetClinicDb.utils.MedicalHistoryToDtoConverter;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Datsenko
 * 26.10.2022
 */
@Controller
@RequestMapping("/cat")
public class CatController {
    private final CatService catService;
    private final ClientService clientService;
    private final MedicalHistoryService medicalHistoryService;

    @Autowired
    public CatController(CatService catService,
                         ClientService clientService,
                         MedicalHistoryService medicalHistoryService) {
        this.catService = catService;
        this.clientService = clientService;
        this.medicalHistoryService = medicalHistoryService;
    }

    @ModelAttribute("role")
    private String addRole() {
        return AuthenticationGetter.getStaffRole();
    }

    @GetMapping("/{id}")
    private String showCat(@PathVariable("id") int id, Model model) {
        Optional<Cat> catOptional = catService.findById(id);
        if (catOptional.isPresent()) {
            Cat cat = catOptional.get();
            CatDto catDto = CatToDtoConverter.convertCatToDto(cat);
            List<MedicalHistory> medicalHistoryList = medicalHistoryService.findAllByCat(cat);
            List<MedicalHistoryDto> medicalHistoryDtoList = MedicalHistoryToDtoConverter
                    .convertMedicalHistoryListToDto(medicalHistoryList);
            model.addAttribute("cat", catDto);
            model.addAttribute("medical_history", medicalHistoryDtoList);
        }
        return "cat/cat";
    }

    @GetMapping("/new/{clientId}")
    private String showCatAddForm(@PathVariable("clientId") int clientId, Model model){
        CatDto catDto = new CatDto();
        catDto.setClientId(clientId);
        model.addAttribute("cat", catDto);
        return "cat/new";
    }

    @PostMapping
    private String createCat(@ModelAttribute("cat") @Valid CatDto catDto,
                             BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            return "cat/new";

        fillOwnerByClientIdField(catDto);

        Cat cat = CatToDtoConverter.convertDtoToCat(catDto);
        catService.save(cat);

        return "redirect:/client/" + catDto.getClientId();
    }

    @GetMapping("/{id}/edit")
    private String showCatEditForm(@PathVariable("id") int id, Model model) {
        Optional<Cat> catOptional = catService.findById(id);
        catOptional.ifPresent(cat -> model.addAttribute("cat",
                CatToDtoConverter.convertCatToDto(cat)));
        return "cat/edit";
    }

    @PatchMapping("/{id}")
    private String editCat(@PathVariable("id") int id,
                           @ModelAttribute("cat") CatDto catDto,
                           BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            return "cat/edit";
        try{
            fillOwnerByClientIdField(catDto);

            Cat updatedCat = CatToDtoConverter.convertDtoToCat(catDto);
            catService.update(updatedCat, id);
            return "redirect:/client/" + updatedCat.getOwner().getId();
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("name", "", e.getCause().getCause().getMessage());
            return "cat/edit";
        }
    }

    @DeleteMapping("/{id}")
    private String deleteCat(@PathVariable("id") int id) throws NotFoundException {
        Optional<Cat> optionalCat = catService.findById(id);
        if (optionalCat.isEmpty()) {
            throw new NotFoundException("Cat not found by id");
        } else {
            int clientId = optionalCat.get().getOwner().getId();
            catService.delete(id);
            return "redirect:/client/" + clientId;
        }
    }

    private void fillOwnerByClientIdField(CatDto catDto) throws NotFoundException {
        Optional<Client> optionalClient = clientService.findById(catDto.getClientId());
        if (optionalClient.isEmpty()) {
            throw new NotFoundException("Client not found by id");
        } else {
            catDto.setOwner(optionalClient.get());
        }
    }
}
