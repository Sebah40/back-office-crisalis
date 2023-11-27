package com.orange.Crisalis.controller;

import ch.qos.logback.core.net.server.Client;
import com.orange.Crisalis.dto.DiscountServiceDTO;
import com.orange.Crisalis.dto.DiscountServiceGroupedDTO;
import com.orange.Crisalis.model.ClientDiscountServiceEntity;
import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.SellableGood;
import com.orange.Crisalis.model.dto.ClientDTO;
import com.orange.Crisalis.model.dto.EnterpriseDTO;
import com.orange.Crisalis.repository.IClientRepository;
import com.orange.Crisalis.repository.IEnterpriseRepository;
import com.orange.Crisalis.repository.IPersonRepository;
import com.orange.Crisalis.service.ClientEnterprisePersonService;
import com.orange.Crisalis.service.ClientService;
import com.orange.Crisalis.service.SellableGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
    @GetMapping("/discountServices/{clientId}")
    public ResponseEntity<Set<ClientDiscountServiceEntity>> getDiscountServices(@PathVariable("clientId") int clientId) {
        Set<ClientDiscountServiceEntity> discountServices = clientService.getDiscountServices(clientId);
        if (discountServices != null && !discountServices.isEmpty()) {
            return ResponseEntity.ok(discountServices);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/discountServicesInRange")
    public Set<DiscountServiceDTO> getDiscountServicesInRange(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        if (startDate == null) {
            startDate = Date.from(Date.from(Instant.parse("1980-01-13T00:00:00Z")).toInstant());
        }

        if (endDate == null) {
            endDate = Date.from(Date.from(Instant.parse("2300-01-13T23:59:59Z")).toInstant());
        }
        return clientService.getDiscountServicesInRange(startDate,endDate);
    }

    @GetMapping("/maxDiscountServicesInRange")
    public ResponseEntity<Set<DiscountServiceDTO>> getMaxDiscountServicesInRange(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {


        if (startDate == null) {
            startDate = Date.from(Date.from(Instant.parse("1980-01-13T00:00:00Z")).toInstant());
        }

        if (endDate == null) {
            endDate = Date.from(Date.from(Instant.parse("2300-01-13T23:59:59Z")).toInstant());
        }
        Set<DiscountServiceDTO> maxDiscountServicesInRange = clientService.getMaxDiscountServiceInRange(startDate, endDate);

        if (maxDiscountServicesInRange.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(maxDiscountServicesInRange);
    }

    @GetMapping("/discountsGroupedByClientAndService")
    public ResponseEntity<List<DiscountServiceGroupedDTO>> getDiscountsGroupedByClientAndService(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {


        if (startDate == null) {
            startDate = Date.from(Date.from(Instant.parse("1980-01-13T00:00:00Z")).toInstant());
        }
        return clientService.getDiscountsGroupedByClientAndService(startDate,endDate);
    }
}