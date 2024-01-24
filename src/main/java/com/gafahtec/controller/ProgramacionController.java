package com.gafahtec.controller;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gafahtec.dto.request.ProgramacionRequest;
import com.gafahtec.dto.response.ProgramacionResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.service.IProgramacionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/programaciones")
@AllArgsConstructor
@Log4j2
public class ProgramacionController {

    private IProgramacionService iProgramacionService;



    @GetMapping
    public ResponseEntity<Set<ProgramacionResponse>> listar() throws Exception {
        var lista = iProgramacionService.listar();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramacionResponse> listarPorId(@PathVariable("id") Integer id) throws Exception {
        var obj = iProgramacionService.listarPorId(id);

        if (obj.getIdProgramacion() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping("/activo")
    public ResponseEntity<ProgramacionResponse> programacionActivo() throws Exception {
        var list = iProgramacionService.programacionActivo().stream().findFirst().orElseThrow(() ->  new ResourceNotFoundException("programacion activa no encontrado "));
        

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ProgramacionResponse> registrar(@Valid @RequestBody ProgramacionRequest programacionRequest)
            throws Exception {


        
        var obj = iProgramacionService.registrar(programacionRequest);

        return new ResponseEntity<>(obj, HttpStatus.CREATED);

    }



    @PutMapping
    public ResponseEntity<ProgramacionResponse> modificar(@Valid @RequestBody ProgramacionRequest programacionRequest) throws Exception {
        var obj = iProgramacionService.modificar(programacionRequest);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iProgramacionService.listarPorId(id);

        if (obj.getIdProgramacion() == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }

        iProgramacionService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{idEmpresa}/pageable")
    public ResponseEntity<Page<ProgramacionResponse>> listarPageable(
            @PathVariable("idEmpresa") Integer idEmpresa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception {
        
        var paging = PageRequest.of(page, size, Sort.by("idProgramacion").descending());
        var paginas = iProgramacionService.listarProgramacionPageable(idEmpresa,paging);
        

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }
}
