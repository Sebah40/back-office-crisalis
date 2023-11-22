package com.orange.Crisalis.security.Controller;

import com.orange.Crisalis.dto.ChangePasswordRequestDTO;
import com.orange.Crisalis.dto.RecoverPasswordRequestDTO;
import com.orange.Crisalis.dto.UserProfileEditDTO;
import com.orange.Crisalis.security.Dto.DisableUser;
import com.orange.Crisalis.security.Dto.EditUser;
import com.orange.Crisalis.security.Dto.GetUserDTO;
import com.orange.Crisalis.security.Entity.RoleEntity;
import com.orange.Crisalis.security.Entity.UserEntity;
import com.orange.Crisalis.security.Enums.RoleName;
import com.orange.Crisalis.security.Repository.IUserRepository;
import com.orange.Crisalis.security.Service.JWTService;
import com.orange.Crisalis.security.Service.RoleService;
import com.orange.Crisalis.security.Service.UserService;
import com.orange.Crisalis.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    JWTService jwtService;
    @Autowired
    PasswordService passwordService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @PostMapping("/disable")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> disable(@Valid @RequestBody DisableUser disableUser
            ,@RequestHeader("Authorization") String authorizationHeader
            , BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Usuario no existe"), HttpStatus.BAD_REQUEST);


        String token = getTokenByHeaders(authorizationHeader);
        String username = jwtService.getUsernameByToken(token);

        if(username.equalsIgnoreCase(disableUser.getUsername())){
            return new ResponseEntity(new Message("No puedes eliminarte tu mismo"),HttpStatus.BAD_REQUEST);

        }

        UserEntity user = iUserRepository.findByUsername(disableUser.getUsername()).get();

        user.setActive(false);
        iUserRepository.save(user);
        return new ResponseEntity(new Message("Deshabilitado exitosamente"),HttpStatus.OK);
    }
//    @GetMapping("/getAll")
//    public ResponseEntity<List<List>> listUser(){
//        return ResponseEntity.ok(userService.findAll().stream().map(UserEntity::getUser).collect(Collectors.toList()));
//    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<GetUserDTO>> listUser(){
        List<UserEntity> users = userService.findAll();
        return ResponseEntity.ok(users.stream()
                .filter(UserEntity::isActive)
                .map(GetUserDTO::new)
                .collect(Collectors.toList()));
    }



    @PostMapping("/edit-user")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> editUserHandler(@Valid @RequestBody EditUser editUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("Usuario no existe"), HttpStatus.BAD_REQUEST);
        UserEntity user = iUserRepository.findByUsername(editUser.getUsername()).get();
        user.setEmail(editUser.getEmail());
        user.setName(editUser.getName());
        if(!editUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(editUser.getPassword()));
        }
        if(!editUser.getRoles().isEmpty()){
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());

            if(editUser.getRoles().contains("admin"))
                roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
            else {
                roles.clear();
                roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
            }
            user.setRoles(roles);
            iUserRepository.save(user);
            return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
        }



        /*user.setRoles(editUser.getRoles());*/
        iUserRepository.save(user);
        return new ResponseEntity(new Message("Editado exitosamente"),HttpStatus.OK);
    }
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authorizationHeader){

        String token = getTokenByHeaders(authorizationHeader);

        return new ResponseEntity<>(userService.getProfileByUsername(jwtService.getUsernameByToken(token)),HttpStatus.OK);

    }

    @PutMapping("/edit-profile")
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public ResponseEntity<Message> editProfile(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody UserProfileEditDTO userEdited){
        String token = getTokenByHeaders(authorizationHeader);
        userService.editProfileByUsername(jwtService.getUsernameByToken(token),userEdited);

        return new ResponseEntity<Message>(new Message("Se edito correctamente"),HttpStatus.OK);

    }
    @PutMapping("/edit-password")
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public ResponseEntity<Message> editPassword(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        String token = getTokenByHeaders(authorizationHeader);


        UserEntity existingUser = userService.getByUserName(jwtService.getUsernameByToken(token)).orElseThrow(null);
        if (!passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(), existingUser.getPassword())) {
            return new ResponseEntity<>(new Message(("La contraseña es incorrecta")),HttpStatus.BAD_REQUEST);
        }

        existingUser.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
        userService.save(existingUser);
        return new ResponseEntity<>(new Message("La contraseña se cambio correctamente"),HttpStatus.OK);
    }

    @PostMapping("/recover-password")
    public ResponseEntity<?> recoverPass(@RequestBody RecoverPasswordRequestDTO recoverPasswordRequestDTO){
        passwordService.sendPasswordByEmail(recoverPasswordRequestDTO.getEmail());
        return new ResponseEntity<>(new Message("Contraseña nueva enviada al correo"),HttpStatus.OK);
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<Message> uploadPhoto(@RequestPart MultipartFile file, Principal principal) {
        String username = principal.getName();
        String fileName = username + "_profile.jpg";

        try {

            Path filePath = Paths.get(uploadDir, fileName);
            file.transferTo(filePath.toFile());

            String imageUrl = "http://localhost:3000/user/" + fileName;

            UserEntity user = userService.getByUserName(username).orElseThrow(null);

            user.setPhoto(imageUrl);

            userService.save(user);

            return new ResponseEntity<>(new Message("Se subio correctamente"),HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    private String getTokenByHeaders(String authorizationHeader){

        return authorizationHeader.substring(6);
    }


}
