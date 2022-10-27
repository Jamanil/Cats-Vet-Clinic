package ru.jamanil.catVetClinicDb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jamanil.catVetClinicDb.models.Client;
import ru.jamanil.catVetClinicDb.repositories.ClientRepository;
import ru.jamanil.catVetClinicDb.security.staff.utils.AuthenticationGetter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Datsenko
 * 25.10.2022
 */
@Service
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(int id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

    public Optional<Client> findByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Transactional
    public void save(Client client) {
        String staffName = AuthenticationGetter.getStaffName();
        Date date = new Date();
        client.setCreatedAt(date);
        client.setCreatedBy(staffName);
        client.setUpdatedAt(date);
        client.setUpdatedBy(staffName);
        clientRepository.save(client);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public void update(Client updatedClient, int id) {
        Optional<Client> clientFromDb = findById(id);
        if(clientFromDb.isPresent()) {
            clientFromDb.get().setName(updatedClient.getName());
            clientFromDb.get().setDob(updatedClient.getDob());
            clientFromDb.get().setAddress(updatedClient.getAddress());
            clientFromDb.get().setEmail(updatedClient.getEmail());
            clientFromDb.get().setPhone(updatedClient.getPhone());
            clientFromDb.get().setUpdatedAt(new Date());
            clientFromDb.get().setUpdatedBy(AuthenticationGetter.getStaffName());
            save(clientFromDb.get());
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        clientRepository.deleteById(id);
    }
}
