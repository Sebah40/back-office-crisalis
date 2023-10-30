package com.orange.Crisalis.controller;
import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.dto.EnterpriseDTO;
import com.orange.Crisalis.model.dto.NewEnterpriseDTO;
import com.orange.Crisalis.repository.IEnterpriseRepository;
import com.orange.Crisalis.security.Controller.Message;
import com.orange.Crisalis.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @Autowired
    IEnterpriseRepository iEnterpriseRepository;

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody NewEnterpriseDTO newEnterpriseDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Error en el formato de los campos"),HttpStatus.BAD_REQUEST);

        if(enterpriseService.existsByCuit(newEnterpriseDTO.getCuit()))
            return new ResponseEntity(new Message("Ese CUIT pertenece a una empresa ya registrada"), HttpStatus.BAD_REQUEST);

        if(enterpriseService.existsByBusinessName(newEnterpriseDTO.getBusinessName()))
            return new ResponseEntity(new Message("Esta razón social se encuentra asociada a otra empresa"), HttpStatus.BAD_REQUEST);

        if(enterpriseService.existsByBusinessName(newEnterpriseDTO.getBusinessName()))
            return new ResponseEntity(new Message("Esta razón social se encuentra asociada a otra empresa"), HttpStatus.BAD_REQUEST);


        EnterpriseEntity enterprise = new EnterpriseEntity(newEnterpriseDTO.isBeneficiary(),
                newEnterpriseDTO.getBusinessName(), newEnterpriseDTO.getCuit(), newEnterpriseDTO.getDate(),
                newEnterpriseDTO.isActive(), newEnterpriseDTO.getFirstNameResponsible(),
                newEnterpriseDTO.getLastNameResponsible(), newEnterpriseDTO.getDniResponsible());

        enterpriseService.save(enterprise);

        return new ResponseEntity(new Message("Empresa guardada satisfactoriamente"),HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<EnterpriseDTO>> listEnterprise(){
        List<EnterpriseEntity> enterprises = iEnterpriseRepository.findAll();
        return ResponseEntity.ok(enterprises.stream()
                .filter(enterprise -> enterprise.isActive())
                .map(EnterpriseDTO::new)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/edit")
    public ResponseEntity<?> editEnterpriseHandler(@Valid @RequestBody EnterpriseDTO enterpriseDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("La empresa no existe"), HttpStatus.BAD_REQUEST);
        EnterpriseEntity enterprise = iEnterpriseRepository.findById(enterpriseDTO.getId()).get();
        enterprise.setCuit(enterpriseDTO.getCuit());
        enterprise.setBusinessName(enterpriseDTO.getBusinessName());
        enterprise.setFirstNameResponsible(enterpriseDTO.getFirstNameResponsible());
        enterprise.setLastNameResponsible(enterpriseDTO.getLastNameResponsible());
        enterprise.setDniResponsible(enterpriseDTO.getDniResponsible());

        /*             IMPORTANTE:
        * FALTA ACÁ AGREGAR PARA EDITAR LA PERSONA RESPONSABLE DE LA EMEPRESA,
        * PERO HAY QUE VER CÓMO IMPLEMENTAMOS ESO TODAVÍA       */

        iEnterpriseRepository.save(enterprise);
        return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/disable")
    public ResponseEntity<?> disable(@Valid @RequestBody EnterpriseDTO enterpriseDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("La empresa no existe"), HttpStatus.BAD_REQUEST);
        EnterpriseEntity enterprise = iEnterpriseRepository.findById(enterpriseDTO.getId()).get();
        enterprise.setActive(false);
        iEnterpriseRepository.save(enterprise);
        return new ResponseEntity(new Message("Empresa deshabilitado exitosamente"),HttpStatus.OK);
    }
}