package com.orange.Crisalis.security.Controller;

import com.orange.Crisalis.security.Dto.DisableUser;
import com.orange.Crisalis.security.Dto.EditUser;
import com.orange.Crisalis.security.Dto.GetUser;
import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Repository.IUserRepository;
import com.orange.Crisalis.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    IUserRepository iusuarioRepository;
    @PostMapping("/disable")
    public ResponseEntity<?> disable(@Valid @RequestBody DisableUser disableUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Usuario no existe"), HttpStatus.BAD_REQUEST);
        UserEntity user = iusuarioRepository.findByUsername(disableUser.getUsername()).get();
        user.setActive(false);
        iusuarioRepository.save(user);
        return new ResponseEntity(new Message("Deshabilitado exitosamente"),HttpStatus.OK);
    }
//    @GetMapping("/getAll")
//    public ResponseEntity<List<List>> listUser(){
//        return ResponseEntity.ok(userService.findAll().stream().map(UserEntity::getUser).collect(Collectors.toList()));
//    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetUser>> listUser(){
        List<UserEntity> users = userService.findAll();
        return ResponseEntity.ok(users.stream().map(GetUser::new).collect(Collectors.toList()));
    }
    @PostMapping("/editPassword")
    public ResponseEntity<?> editPassword(@Valid @RequestBody EditUser editUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Usuario no existe"), HttpStatus.BAD_REQUEST);
        UserEntity user = iusuarioRepository.findByUsername(editUser.getUsername()).get();
        user.setPassword(passwordEncoder.encode(editUser.getPassword()));
        iusuarioRepository.save(user);
        return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
    }
    @PostMapping("/editName")
    public ResponseEntity<?> editName(@Valid @RequestBody EditUser editUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Usuario no existe"), HttpStatus.BAD_REQUEST);
        UserEntity user = iusuarioRepository.findByUsername(editUser.getUsername()).get();
        user.setName(editUser.getName());
        iusuarioRepository.save(user);
        return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
    }
    @PostMapping("/editEmail")
    public ResponseEntity<?> editEmail(@Valid @RequestBody EditUser editUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Usuario no existe"), HttpStatus.BAD_REQUEST);
        UserEntity user = iusuarioRepository.findByUsername(editUser.getUsername()).get();
        user.setEmail(editUser.getEmail());
        iusuarioRepository.save(user);
        return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
    }
}
