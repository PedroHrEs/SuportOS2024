package com.curso.resources;

import com.curso.domains.ServiceOrder;
import com.curso.domains.dtos.ServiceOrderDTO;
import com.curso.services.ServiceOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value ="/serviceorder")
@Tag(name = "Ordem de servico", description = "API para Gerenciamento de Order de Servico")
public class ServiceOrderResource {

    @Autowired
    private ServiceOrderService osService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca uma Ordem de Servico por id",
            description = "Realiza a busca de uma Ordem de Servico cadastrado por id")
    public ResponseEntity<ServiceOrderDTO> findById(@PathVariable UUID id){
        ServiceOrder obj = this.osService.findById(id);
        return ResponseEntity.ok().body(new ServiceOrderDTO(obj));
    }

    @GetMapping
    @Operation(summary = "Listar todas as Ordens de Servico",
            description = "Retorna uma lista com todas as Ordens de Servico cadastrados")
    public ResponseEntity<List<ServiceOrderDTO>> findAll(){return ResponseEntity.ok().body(osService.findAll());}

    @PostMapping
    @Operation(summary = "Criar uma nova Ordem de Servico",
            description = "Cria uma nova Ordem de Servico com base nos dados fornecidos")
    public ResponseEntity<ServiceOrderDTO> create(@Valid @RequestBody ServiceOrderDTO objDto){
        ServiceOrder newObj = osService.create(objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PutMapping(value="/{id}")
    @Operation(summary = "Altera uma Ordem de Servico",
            description = "Altera uma Ordem de Servico existente")
    public ResponseEntity<ServiceOrderDTO> update(@PathVariable UUID id, @Valid @RequestBody ServiceOrderDTO objDto){
        ServiceOrder Obj = osService.update(id, objDto);
        return ResponseEntity.ok().body(new ServiceOrderDTO(Obj));
    }
}
