package com.mindHub.HomeBanking.services.implement;

import com.mindHub.HomeBanking.dtos.ClientDTO;
import com.mindHub.HomeBanking.models.Client;
import com.mindHub.HomeBanking.repositories.ClientRepository;
import com.mindHub.HomeBanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImp implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClientsDTO(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }

    @Override
    public Client getCurrentClient(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public ClientDTO getClientDTO(Long id) {
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client clientFindByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
