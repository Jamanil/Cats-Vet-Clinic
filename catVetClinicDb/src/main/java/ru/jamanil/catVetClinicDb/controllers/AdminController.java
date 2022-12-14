package ru.jamanil.catVetClinicDb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jamanil.catVetClinicDb.security.staff.Staff;
import ru.jamanil.catVetClinicDb.security.staff.dto.StaffDto;
import ru.jamanil.catVetClinicDb.security.staff.services.StaffService;
import ru.jamanil.catVetClinicDb.security.staff.utils.StaffToDtoConverter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final StaffService staffService;

    @GetMapping
    private String showUsers(Model model) {
        List<Staff> staffList = staffService.findAll();
        List<StaffDto> staffDtoList = new ArrayList<>();
        for (Staff staff : staffList) {
            staffDtoList.add(StaffToDtoConverter.convertStaffToDto(staff));
        }
        model.addAttribute("staff", staffDtoList);
        return "/admin/users";
    }

    @GetMapping("/{id}")
    private String showEditUserForm(@PathVariable("id") long id, Model model) {
        Optional<Staff> staff = staffService.findById(id);
        staff.ifPresent(value -> model.addAttribute("user", StaffToDtoConverter.convertStaffToDto(value)));
        return "/admin/user";
    }

    @PatchMapping("/{id}")
    private String editUser(@PathVariable("id") long id,
                            @ModelAttribute("user") @Valid StaffDto staffDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/user";
        } else {
            try {
                staffService.update(StaffToDtoConverter.convertDtoToStaff(staffDto), id);
                return "redirect:/admin";
            } catch (DataIntegrityViolationException e) {
                bindingResult.rejectValue("name", "", e.getCause().getCause().getMessage());
                return "/admin/user";
            }
        }
    }

    @DeleteMapping("/{id}")
    private String deleteUser(@PathVariable("id") long id) {
        staffService.delete(id);
        return "redirect:/admin";
    }
}
