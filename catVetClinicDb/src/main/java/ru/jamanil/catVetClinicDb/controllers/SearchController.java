package ru.jamanil.catVetClinicDb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.jamanil.catVetClinicDb.dao.ClientSearchDao;
import ru.jamanil.catVetClinicDb.dto.ClientDto;
import ru.jamanil.catVetClinicDb.models.Client;
import ru.jamanil.catVetClinicDb.utils.ClientToDtoConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Datsenko
 * 27.10.2022
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    private final ClientSearchDao clientSearchDao;

    @Autowired
    public SearchController(ClientSearchDao clientSearchDao) {
        this.clientSearchDao = clientSearchDao;
    }

    @PostMapping
    private String search(@RequestParam("search_by") String searchBy,
                          @RequestParam("substring_for_search") String substringForSearch,
                          Model model) {
        try {
            List<Client> foundedClients = clientSearchDao.findBySubstring(searchBy, substringForSearch);
            List<ClientDto> foundedClientsDto = new ArrayList<>();
            for (Client client : foundedClients) {
                foundedClientsDto.add(ClientToDtoConverter.convertClientToDto(client));
            }
            model.addAttribute("foundedClients", foundedClientsDto);
        } catch (BadSqlGrammarException e) {
            System.out.println("Incorrect searchBy field value");
        }
        return "/search/search_result";
    }
}
