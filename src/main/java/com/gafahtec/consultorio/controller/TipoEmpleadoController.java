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

import com.gafahtec.consultorio.dto.request.TipoEmpleadoRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.TipoEmpleado;
import com.gafahtec.consultorio.service.ITipoEmpleadoService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/tipoEmpleados")
@AllArgsConstructor
@Log4j2
public class TipoEmpleadoController {

    private ITipoEmpleadoService iTipoEmpleadoService;

    @PostMapping
    public ResponseEntity<TipoEmpleado> registrar(@Valid @RequestBody TipoEmpleadoRequest horarioRequest)
            throws Exception {
        TipoEmpleado horario = TipoEmpleado.builder().build();
        BeanUtils.copyProperties(horario, horarioRequest);
        TipoEmpleado obj = iTipoEmpleadoService.registrar(horario);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<TipoEmpleado>> listarPageable(
            @PageableDefault(sort = "idTipoEmpleado") Pageable pageable, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception {
        Page<TipoEmpleado> paginas = iTipoEmpleadoService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<TipoEmpleado>>(paginas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEmpleado> listarPorId(@PathVariable("id") Integer id) throws Exception {
        TipoEmpleado obj = iTipoEmpleadoService.listarPorId(id);

        if (obj.getIdTipoEmpleado() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<TipoEmpleado>(obj, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TipoEmpleado>> listar() throws Exception {
        List<TipoEmpleado> lista = iTipoEmpleadoService.listar();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TipoEmpleado>>(lista, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/eliminar")
    public ResponseEntity<Integer> eliminar(@RequestBody TipoEmpleadoRequest tipoEmpleadoRequest) throws ResourceNotFoundException {
        iTipoEmpleadoService.eliminar(tipoEmpleadoRequest.getIdTipoEmpleado());
        return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping
    public ResponseEntity<TipoEmpleado> modificar(@Valid @RequestBody TipoEmpleadoRequest horarioRequest)
            throws Exception {
        TipoEmpleado horario = TipoEmpleado.builder().build();
        BeanUtils.copyProperties(horario, horarioRequest);
        TipoEmpleado obj = iTipoEmpleadoService.modificar(horario);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
