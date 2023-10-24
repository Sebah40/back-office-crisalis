package com.orange.Crisalis.controller;
import com.orange.Crisalis.model.ClientEntity;
import com.orange.Crisalis.model.dto.ClientDTO;
import com.orange.Crisalis.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

  @GetMapping("/getAll")
    public ResponseEntity<List<ClientDTO>> listClient(){
        List<ClientEntity> clients = clientService.findAll();
        return ResponseEntity.ok(clients.stream().filter(client -> client.isActive()).map(GetClient::new).collect(Collectors.toList()));
    }
   /*   CÓMO ACOMODAR EL "isActive"?     */

/*    @PostMapping("/edit-client")
    public ResponseEntity<?> editUserHandler(@Valid @RequestBody EditUser editUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Usuario no existe"), HttpStatus.BAD_REQUEST);
        UserEntity user = iusuarioRepository.findByUsername(editUser.getUsername()).get();
        user.setEmail(editUser.getEmail());
        user.setName(editUser.getName());
        if(!editUser.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(editUser.getPassword()));
        }
        *//*user.setRoles(editUser.getRoles());*//*
        iusuarioRepository.save(user);
        return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
    }
    MODIFICAR PARA CLIENT. EL TEMA ES QUE CÓMO LO MODIFICO? TENGO QUE HACER UNO PARA EMPRESA Y OTRO PARA PERSONA FÍSICA?
    */
}