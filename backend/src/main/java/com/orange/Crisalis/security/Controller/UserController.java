package com.orange.Crisalis.security.Controller;

import com.orange.Crisalis.security.Dto.DisableUsuario;
import com.orange.Crisalis.security.Dto.LoginUsuario;
import com.orange.Crisalis.security.Entity.Usuario;
import com.orange.Crisalis.security.Repository.iUsuarioRepository;
import com.orange.Crisalis.security.Service.UsuarioService;
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
    UsuarioService usuarioService;
    @Autowired
    iUsuarioRepository iusuarioRepository;
    @PostMapping("/disable")
    public ResponseEntity<?> disable(@Valid @RequestBody DisableUsuario disableUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Usuario no existe"), HttpStatus.BAD_REQUEST);
        Usuario user = iusuarioRepository.findByNombreUsuario(disableUsuario.getNombreUsuario()).get();
        user.setActive(false);
        iusuarioRepository.save(user);
        return new ResponseEntity(new Mensaje("Deshabilitado exitosamente"),HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<List>> listUser(){
        return ResponseEntity.ok(usuarioService.findAll().stream().map(Usuario::getUser).collect(Collectors.toList()));
    }
    @PostMapping("/edit")
    public ResponseEntity<?> edit(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Usuario no existe"), HttpStatus.BAD_REQUEST);
        Usuario user = iusuarioRepository.findByNombreUsuario(loginUsuario.getNombreUsuario()).get();
        user.setPassword(passwordEncoder.encode(loginUsuario.getEditPassword()));
        user.setNombreUsuario(loginUsuario.getEditUser());
        iusuarioRepository.save(user);
        return new ResponseEntity(new Mensaje("Editado exitosamente"),HttpStatus.OK);
    }
}
