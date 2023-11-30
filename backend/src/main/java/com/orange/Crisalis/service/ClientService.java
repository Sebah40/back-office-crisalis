package com.orange.Crisalis.service;

import com.orange.Crisalis.dto.DiscountServiceDTO;
import com.orange.Crisalis.dto.DiscountServiceGroupedDTO;
import com.orange.Crisalis.model.ClientDiscountServiceEntity;
import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.repository.ClientDiscountServiceRepository;
import com.orange.Crisalis.repository.IClientRepository;
import com.orange.Crisalis.repository.IEnterpriseRepository;
import com.orange.Crisalis.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {
    @Autowired
    ClientDiscountServiceRepository discountServiceRepository;
    @Autowired
    IClientRepository iClientRepository;
    @Autowired
    IPersonRepository iPersonRepository;
    @Autowired
    IEnterpriseRepository iEnterpriseRepository;


    public List<ClientEntity> findAll(){
        return iClientRepository.findAll();
    }
    public ClientEntity getById(Integer id){
        Optional<ClientEntity> clienteOptional = iClientRepository.findById(id);
        return clienteOptional.orElse(null);
    }
    public Set<ClientDiscountServiceEntity> getDiscountServices(int clientId) {
        Optional<ClientEntity> clientOptional = iClientRepository.findById(clientId);
        return clientOptional.get().getDiscountServices();
    }


    public ClientEntity saveClient(ClientEntity client) {
        return iClientRepository.save(client);
    }


    public Set<ClientDiscountServiceEntity> getAllDiscountServices(Date startDate, Date endDate) {
        List<ClientDiscountServiceEntity> discountServicesInRange = discountServiceRepository.findByOrderDateBetween(startDate, endDate);
        return discountServicesInRange.stream()
                .collect(Collectors.toSet());
    }
    public Set<DiscountServiceDTO> getDiscountServicesInRange(Date startDate, Date endDate) {
        Set<DiscountServiceDTO> discountServicesInRangeDTO = getAllDiscountServices(startDate, endDate).stream()

                .map(entity -> {
                    DiscountServiceDTO dto = new DiscountServiceDTO();
                    dto.setClientID(entity.getId());
                    dto.setClientID(entity.getClient().getId());
                    iPersonRepository.findById(dto.getClientID())
                            .ifPresent(person -> dto.setClientName(person.getFirstName() + " " + person.getLastName()));
                    iEnterpriseRepository.findById(dto.getClientID())
                            .ifPresent(company -> dto.setClientName(company.getBusinessName()));
                    dto.setService(entity.getSellableGood().getName());
                    dto.setDiscount(entity.getDiscount());
                    dto.setOrderID(entity.getId());
                    return dto;
                })
                .collect(Collectors.toSet());
        return discountServicesInRangeDTO;
    }

    public Set<DiscountServiceDTO> getMaxDiscountServiceInRange(Date startDate, Date endDate) {
        Map<Integer, DiscountServiceDTO> maxDiscountByClient = getAllDiscountServices(startDate, endDate).stream()
                .filter(Objects::nonNull)
                .filter(dto -> dto.getDiscount() > 0)
                .collect(Collectors.toMap(
                        entity -> entity.getClient().getId(),
                        dto -> {
                            DiscountServiceDTO result = new DiscountServiceDTO();
                            result.setClientID(dto.getClient().getId());
                            result.setService(dto.getSellableGood().getName());
                            result.setDiscount(dto.getDiscount());
                            result.setOrderID(dto.getOrder().getId().intValue());
                            iPersonRepository.findById(result.getClientID())
                                    .ifPresent(person -> result.setClientName(person.getFirstName() + " " + person.getLastName()));
                            iEnterpriseRepository.findById(result.getClientID())
                                    .ifPresent(company -> result.setClientName(company.getBusinessName()));
                            return result;
                        },
                        BinaryOperator.maxBy(Comparator.comparingDouble(DiscountServiceDTO::getDiscount))
                ));

        return new HashSet<>(maxDiscountByClient.values());
    }

    public Set<DiscountServiceGroupedDTO> getDiscountServicesInRangeGroup(Date startDate, Date endDate) {

        Set<DiscountServiceGroupedDTO> discountServicesInRangeDTO = getAllDiscountServices(startDate, endDate).stream()

                .map(entity -> {
                    DiscountServiceGroupedDTO dto = new DiscountServiceGroupedDTO();
                    dto.setClientID(entity.getId());
                    dto.setClientID(entity.getClient().getId());
                    iPersonRepository.findById(dto.getClientID())
                            .ifPresent(person -> dto.setClientName(person.getFirstName() + " " + person.getLastName()));
                    iEnterpriseRepository.findById(dto.getClientID())
                            .ifPresent(company -> dto.setClientName(company.getBusinessName()));
                    dto.setService(entity.getSellableGood().getName());
                    dto.setDiscount(entity.getDiscount());
                    return dto;
                })
                .collect(Collectors.toSet());
        return discountServicesInRangeDTO;
    }

    public ResponseEntity<List<DiscountServiceGroupedDTO>> getDiscountsGroupedByClientAndService(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        Set<DiscountServiceGroupedDTO> discountServicesInRangeDTO = this.getDiscountServicesInRangeGroup(startDate, endDate);

        if (discountServicesInRangeDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Map<Integer, Double>> discountsByClientAndService = discountServicesInRangeDTO.stream()
                .collect(Collectors.groupingBy(
                        dto -> dto.getClientID() + "_" + dto.getService(),
                        Collectors.groupingBy(DiscountServiceGroupedDTO::getClientID, Collectors.summingDouble(DiscountServiceGroupedDTO::getDiscount))
                ));
        List<DiscountServiceGroupedDTO> result = discountsByClientAndService.entrySet().stream()
                .filter(clientEntry -> !Objects.equals(clientEntry.getKey(), 0))
                .flatMap(entry -> {
                    String[] keyParts = entry.getKey().split("_");
                    String service = keyParts[1];
                    int clientId = Integer.parseInt(keyParts[0]);

                    Map<Integer, Double> clientDiscounts = entry.getValue();
                    return clientDiscounts.entrySet().stream()
                            .map(clientEntry -> {
                                DiscountServiceGroupedDTO dto = new DiscountServiceGroupedDTO();
                                dto.setClientID(clientId);
                                iPersonRepository.findById(dto.getClientID())
                                        .ifPresent(person -> dto.setClientName(person.getFirstName() + " " + person.getLastName()));
                                iEnterpriseRepository.findById(dto.getClientID())
                                        .ifPresent(person -> dto.setClientName(person.getFirstNameResponsible() + " " + person.getLastNameResponsible()));
                                dto.setService(service);
                                dto.setDiscount(clientEntry.getValue());
                                return dto;
                            });
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
