package ru.jamanil.catVetClinicDb.controllers;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jamanil.catVetClinicDb.dto.ClientDto;
import ru.jamanil.catVetClinicDb.models.Client;
import ru.jamanil.catVetClinicDb.security.staff.utils.AuthenticationGetter;
import ru.jamanil.catVetClinicDb.services.CatService;
import ru.jamanil.catVetClinicDb.services.ClientService;
import ru.jamanil.catVetClinicDb.utils.ClientDtoValidator;
import ru.jamanil.catVetClinicDb.utils.ClientToDtoConverter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientDtoValidator clientDtoValidator;
    private final CatService catService;

    @ModelAttribute("role")
    private String addRole() {
        return AuthenticationGetter.getStaffRole();
    }

    @GetMapping
    private String showAllClients (Model model) {
        List<Client> clientList = clientService.findAll();
        List<ClientDto> clientDtoList = new ArrayList<>();
        for (Client client: clientList) {
            clientDtoList.add(ClientToDtoConverter.convertClientToDto(client));
        }
        model.addAttribute("clients", clientDtoList);
        return "client/clients";
    }

    @GetMapping("/new")
    private String showAddClientPage(Model model) {
        model.addAttribute("client", new ClientDto());
        return "client/new";
    }

    @PostMapping()
    private String createClient(@ModelAttribute("client") @Valid ClientDto clientDto,
                                BindingResult bindingResult) {
        clientDtoValidator.validate(clientDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "client/new";
        } else {
            Client client = ClientToDtoConverter.convertDtoToClient(clientDto);
            clientService.save(client);
            return "redirect:/client";
        }
    }

    @GetMapping("/{id}")
    private String showClient(@PathVariable("id") long id, Model model) throws NotFoundException {
        Optional<Client> clientOptional = clientService.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            model.addAttribute("client", ClientToDtoConverter.convertClientToDto(client));
            model.addAttribute("cats", catService.findAllByOwner(client));
            return "client/client";
        } else {
            throw new NotFoundException("Client not found by id");
        }
    }

    @GetMapping("/{id}/edit")
    private String showClientEditForm(@PathVariable("id") long id, Model model) {
        Optional<Client> client = clientService.findById(id);
        client.ifPresent(value -> model.addAttribute("client",
                ClientToDtoConverter.convertClientToDto(value)));
        return "client/edit";
    }

    @PatchMapping("/{id}")
    private String updateClient(@PathVariable("id") long id,
                                @ModelAttribute("client") ClientDto clientDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/edit";
        } else {
            try {
                clientService.update(ClientToDtoConverter.convertDtoToClient(clientDto), id);
                return "redirect:/client";
            } catch (DataIntegrityViolationException e) {
                bindingResult.rejectValue("name", "", e.getCause().getCause().getMessage());
                return "client/edit";
            }
        }
    }

    @DeleteMapping("/{id}")
    private String deleteClient(@PathVariable("id") long id) {
        clientService.delete(id);
        return "redirect:/client";
    }
}
