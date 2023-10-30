package com.orange.Crisalis.controller;
import com.orange.Crisalis.model.PersonEntity;
import com.orange.Crisalis.model.dto.NewPersonDTO;
import com.orange.Crisalis.model.dto.PersonDTO;
import com.orange.Crisalis.repository.IPersonRepository;
import com.orange.Crisalis.security.Controller.Message;
import com.orange.Crisalis.service.PersonService;
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
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    IPersonRepository iPersonRepository;

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody NewPersonDTO newPersonDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Error en el formato de los campos"),
                    HttpStatus.BAD_REQUEST);

        if(personService.existsByDni(newPersonDTO.getDni()))
            return new ResponseEntity(new Message("Este DNI se encuentra asociado a otra persona"),
                    HttpStatus.BAD_REQUEST);

        PersonEntity person = new PersonEntity(newPersonDTO.isBeneficiary(), newPersonDTO.getLastName(),
                newPersonDTO.getFirstName(), newPersonDTO.getDni(), newPersonDTO.isActive());

        personService.save(person);

        return new ResponseEntity(new Message("Persona guardada satisfactoriamente"),HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<PersonDTO>> listPerson(){
        List<PersonEntity> persons = iPersonRepository.findAll();
        return ResponseEntity.ok(persons.stream()
                .filter(person -> person.isActive())
                .map(PersonDTO::new)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/edit")
    public ResponseEntity<?> editPersonHandler(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("La persona no existe"), HttpStatus.BAD_REQUEST);
        PersonEntity person = iPersonRepository.findById(personDTO.getId()).get();
        person.setDni(personDTO.getDni());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());

        iPersonRepository.save(person);
        return new ResponseEntity(new Message("Editado exitosamente"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/disable")
    public ResponseEntity<?> disable(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("La persona no existe"), HttpStatus.BAD_REQUEST);
        PersonEntity person = iPersonRepository.findById(personDTO.getId()).get();
        person.setActive(false);
        iPersonRepository.save(person);
        return new ResponseEntity(new Message("Persona deshabilitada exitosamente"),HttpStatus.OK);
    }
}