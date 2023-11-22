package com.orange.Crisalis.controller;

import ch.qos.logback.core.net.server.Client;
import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.SellableGood;
import com.orange.Crisalis.model.dto.ClientDTO;
import com.orange.Crisalis.model.dto.EnterpriseDTO;
import com.orange.Crisalis.repository.IClientRepository;
import com.orange.Crisalis.service.ClientEnterprisePersonService;
import com.orange.Crisalis.service.ClientService;
import com.orange.Crisalis.service.SellableGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = {"http://localhost:4200", "https://localhost:4200"})
public class ClientController {
    @Autowired
    private SellableGoodService sellableGoodService;
    @Autowired
    ClientService clientService;
    @Autowired
    IClientRepository iClientRepository;
    @Autowired
    ClientEnterprisePersonService clientEnterprisePersonService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClient(@PathVariable("id") int id) {
        ClientEntity client = iClientRepository.findById(id).orElse(null);
        return ResponseEntity.ok(client);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ClientEntity>> listEnterprise(){
        List<ClientEntity> clients = iClientRepository.findAll();
        return ResponseEntity.ok(clients);
        //clients.stream()
          //      .map(client -> new ClientDto(client.get))
            //    .collect(Collectors.toList())
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/getByAnyParameter")
    public List<ClientEntity> searchByAnyParameter(@RequestParam String query) {
        return clientEnterprisePersonService.searchByAnyParameter(query);
    }

    @GetMapping("/getServices/{clientId}")
    public Set<SellableGood> returnServices(@PathVariable int clientId) {
        ClientEntity client = clientService.getById(clientId);
        return client.getActiveServices();
    }

    @PostMapping("/{clientId}/removeActiveService/{serviceId}")
    public ResponseEntity<String> removeActiveServiceToClient(
            @PathVariable int clientId, @PathVariable int serviceId) {
        try {
            ClientEntity client = clientService.getById(clientId);
            SellableGood service = sellableGoodService.findById(Long.valueOf(serviceId))
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado."));

            if (client != null && service != null) {
                client.getActiveServices().remove(service);
                clientService.saveClient(client);
                if (client.getActiveServices().isEmpty()) {
                    client.setBeneficiary(false);
                    clientService.saveClient(client);
                    return new ResponseEntity<>("Servicio quitado del cliente. Ya no es beneficiario.", HttpStatus.OK);
                }
                return new ResponseEntity<>("Servicio quitado del cliente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cliente o servicio no encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}