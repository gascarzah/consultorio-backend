package com.gafahtec.consultorio.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.gafahtec.consultorio.dto.request.HorarioRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Horario;
import com.gafahtec.consultorio.service.IHorarioService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/horarios")
@AllArgsConstructor
@Log4j2
public class HorarioController {

    private IHorarioService iHorarioService;

    @PostMapping
    public ResponseEntity<Horario> registrar(@Valid @RequestBody HorarioRequest horarioRequest) throws Exception {
        Horario horario = Horario.builder().build();
        BeanUtils.copyProperties(horario, horarioRequest);
        Horario obj = iHorarioService.registrar(horario);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Horario>> listarPageable(@PageableDefault(sort = "idHorario") Pageable pageable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception {
        Page<Horario> paginas = iHorarioService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Horario>>(paginas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horario> listarPorId(@PathVariable("id") Integer id) throws Exception {
        Horario obj = iHorarioService.listarPorId(id);

        if (obj.getIdHorario() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<Horario>(obj, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Horario>> getHorarios() throws Exception {
        List<Horario> horarios = iHorarioService.listar();
        if (horarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Horario>>(horarios, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iHorarioService.listarPorId(id);
        
        if(obj == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }
        iHorarioService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping
    public ResponseEntity<Horario> modificar(@Valid @RequestBody HorarioRequest horarioRequest) throws Exception {
        Horario horario = Horario.builder().build();
        BeanUtils.copyProperties(horario, horarioRequest);
        Horario obj = iHorarioService.modificar(horario);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
