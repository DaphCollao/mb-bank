package com.mindHub.HomeBanking.services;

import com.mindHub.HomeBanking.dtos.ClientDTO;
import com.mindHub.HomeBanking.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClientsDTO();
    Client getCurrentClient(Authentication authentication);
    ClientDTO getClientDTO(@PathVariable Long id);
    void saveClient(Client client);
    Client clientFindByEmail(String email);
}
