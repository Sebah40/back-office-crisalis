package com.orange.Crisalis.controller;

import com.orange.Crisalis.dto.TaxDto;
import com.orange.Crisalis.exceptions.ResponseMessage;
import com.orange.Crisalis.model.Tax;
import com.orange.Crisalis.security.Controller.Message;
import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Service.UserService;
import com.orange.Crisalis.service.ITaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tax")
@CrossOrigin(origins = {"http://localhost:4200", "https://localhost:4200"})
public class TaxController {

    private ITaxService taxService;
    //private UserService userService;

    @Autowired
    public TaxController(ITaxService taxService) {
        this.taxService = taxService;
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping
    public List<TaxDto> getTaxes() {
        List<TaxDto> taxList = taxService.getTaxes();
        return taxList;
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/{id}")
    public TaxDto getTaxById(@PathVariable("id") Integer id) {
        if(taxService.verifyTaxById(id)) {
            return taxService.getTaxById(id);
        }
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Object> createTax(@Valid @RequestBody TaxDto taxDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ResponseMessage("Hubo un error en el envío", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        if(taxService.verifyTax(taxDto)) {
            taxService.saveTax(taxDto);
            return new ResponseEntity(new Message("Creado con éxito"),HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(new ResponseMessage("Impuesto existente", HttpStatus.IM_USED), HttpStatus.IM_USED);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTax(@RequestBody TaxDto taxDto, @PathVariable("id") Integer id, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(new ResponseMessage("Hubo un error en el envío", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        if(taxService.verifyTaxById(id)) {
            taxService.updateTax(taxDto, id);
            return new ResponseEntity(new Message("Editado con éxito"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("El impuesto con el ID solicitado no existe", HttpStatus.NOT_FOUND)
                    , HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<Object> deleteTax(@Valid @RequestBody Tax tax) {
        System.out.println(tax);
        if(taxService.verifyTaxById(tax.getId())) {
            taxService.deleteTax(tax.getId());
            return new ResponseEntity<>(new ResponseMessage("Eliminado con éxito", HttpStatus.OK), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("El impuesto con el ID solicitado no existe", HttpStatus.NOT_FOUND)
                    , HttpStatus.NOT_FOUND);
        }
    }


}
