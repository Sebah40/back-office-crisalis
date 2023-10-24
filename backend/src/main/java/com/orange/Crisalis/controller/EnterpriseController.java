package com.orange.Crisalis.controller;

import com.orange.Crisalis.model.EnterpriseEntity;
import com.orange.Crisalis.model.dto.EnterpriseDTO;
import com.orange.Crisalis.repository.IEnterpriseRepository;
import com.orange.Crisalis.security.Controller.Message;
import com.orange.Crisalis.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getAll")
    public ResponseEntity<List<EnterpriseDTO>> listEnterprise(){
        List<EnterpriseEntity> enterprises = iEnterpriseRepository.findAll();
        return ResponseEntity.ok(enterprises.stream()
                .map(EnterpriseDTO::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/edit-enterprise")
    public ResponseEntity<?> editEnterpriseHandler(@Valid @RequestBody EnterpriseDTO enterpriseDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("La empresa no existe"), HttpStatus.BAD_REQUEST);
        EnterpriseEntity enterprise = iEnterpriseRepository.findById(enterpriseDTO.getId()).get();
        enterprise.setCuit(enterpriseDTO.getCuit());
        enterprise.setBusinessName(enterpriseDTO.getBusinessName());
        /*             IMPORTANTE:
        * FALTA ACÁ AGREGAR PARA EDITAR LA PERSONA RESPONSABLE DE LA EMEPRESA,
        * PERO HAY QUE VER CÓMO IMPLEMENTAMOS ESO TODAVÍA       */

        iEnterpriseRepository.save(enterprise);
        return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
    }
}