package ru.jamanil.catVetClinicDb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.jamanil.catVetClinicDb.security.staff.Staff;
import ru.jamanil.catVetClinicDb.security.staff.dto.StaffDto;
import ru.jamanil.catVetClinicDb.security.staff.services.RegistrationService;
import ru.jamanil.catVetClinicDb.security.staff.utils.StaffDtoValidator;
import ru.jamanil.catVetClinicDb.security.staff.utils.StaffToDtoConverter;

import javax.validation.Valid;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final StaffDtoValidator staffDtoValidator;

    @Autowired
    public AuthController(RegistrationService registrationService,
                          StaffDtoValidator staffDtoValidator) {
        this.registrationService = registrationService;
        this.staffDtoValidator = staffDtoValidator;
    }

    @GetMapping("/login")
    private String showLoginPage() {
        return "/auth/login";
    }

    @GetMapping("/registration")
    private String showRegistrationPage(@ModelAttribute("staffDto") StaffDto staffDto) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    private String performRegistration(@ModelAttribute("staffDto") @Valid StaffDto staffDto, BindingResult bindingResult) {
        staffDtoValidator.validate(staffDto, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "auth/registration";
        }

        Staff staff = StaffToDtoConverter.convertDtoToStaff(staffDto);
        System.out.println(staff);
        registrationService.register(staff);
        return "redirect:/auth/login";
    }

    @GetMapping("/failure_login")
    private String showFailureLoginPage(Model model) {
        model.addAttribute("error", "Wrong login or password");
        return "auth/login";
    }
}
