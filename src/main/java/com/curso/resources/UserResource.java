package com.curso.resources;


import com.curso.domains.User;
import com.curso.domains.dtos.UserDTO;
import com.curso.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "Usuario", description = "API para Gerenciamento de Usuarios")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos os Usuarios",
            description = "Retorna uma lista com todos os Usuarios cadastrados")
    public ResponseEntity<List<UserDTO>> findAll(){ return ResponseEntity.ok().body(userService.findAll());}

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca um Usuario por id",
            description = "Realiza a busca de um Usuario cadastrado por id")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }

    @GetMapping(value = "/cpf/{cpf}")
    @Operation(summary = "Busca um Usuario por CPF",
            description = "Realiza a busca de um Usuario cadastrado por CPF")
    public ResponseEntity<UserDTO> findByCpf(@PathVariable String cpf){
        User obj = this.userService.findByCpf(cpf);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }

    @GetMapping(value = "/email/{email}")
    @Operation(summary = "Busca um Usuario por Email",
            description = "Realiza a busca de um Usuario cadastrado por Email")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email){
        User obj = this.userService.findByEmail(email);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }
    @PostMapping
    @Operation(summary = "Criar um novo Usuario",
            description = "Cria um novo Usuario com base nos dados fornecidos")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO objDto){
        User newObj = userService.create(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PutMapping(value = "/{id}")
    @Operation(summary = "Altera Usuario",
            description = "Altera um Usuario existente")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO objDto){
        User obj = userService.update(id, objDto);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar um Usuario",
            description = "Remove Usuario a partir do seu Id")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
