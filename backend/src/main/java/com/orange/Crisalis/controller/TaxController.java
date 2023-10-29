package com.orange.Crisalis.controller;

import com.orange.Crisalis.dto.TaxDto;
import com.orange.Crisalis.model.Tax;
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
        return taxService.getTaxById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Object> createTax(@Valid @RequestBody TaxDto taxDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<Object>("Hubo un error en el envío", HttpStatus.BAD_REQUEST);
        }
        taxService.saveTax(taxDto);
        return new ResponseEntity<>("Creado con éxito", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTax(@RequestBody TaxDto taxDto, @PathVariable("id") Integer id, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<Object>("Hubo un error en el envío", HttpStatus.BAD_REQUEST);
        }
        taxService.updateTax(taxDto, id);
        return new ResponseEntity<>("Creado con éxito", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTax(@PathVariable("id") Integer id) {
        taxService.deleteTax(id);
        return new ResponseEntity<>("Impuesto eliminado", HttpStatus.OK);
    }


}
