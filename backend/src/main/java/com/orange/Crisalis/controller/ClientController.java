package com.orange.Crisalis.controller;

import ch.qos.logback.core.net.server.Client;
import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.dto.ClientDTO;
import com.orange.Crisalis.model.dto.EnterpriseDTO;
import com.orange.Crisalis.repository.IClientRepository;
import com.orange.Crisalis.service.ClientEnterprisePersonService;
import com.orange.Crisalis.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = {"http://localhost:4200", "https://localhost:4200"})
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    IClientRepository iClientRepository;
    @Autowired
    ClientEnterprisePersonService clientEnterprisePersonService;

/*    @GetMapping("/getAll")
    public ResponseEntity<List<ClientDTO>> listEnterprise(){
        List<ClientEntity> clients = iClientRepository.findAll();
        return ResponseEntity.ok(clients.stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList()));
    }*/

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/getByAnyParameter")
    public List<ClientEntity> searchByAnyParameter(@RequestParam String query) {
        return clientEnterprisePersonService.searchByAnyParameter(query);
    }
}