package com.gafahtec.controller;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gafahtec.dto.request.EmpleadoRequest;
import com.gafahtec.dto.response.EmpleadoResponse;
import com.gafahtec.service.IEmpleadoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/empleados")
@AllArgsConstructor
@Log4j2
public class EmpleadoController {

    private IEmpleadoService iEmpleadoService;
    
    @GetMapping(value = "/medicos/{idRol}")
    public ResponseEntity<Set<EmpleadoResponse>> listar(@PathVariable("idRol") Integer idRol) throws Exception {
        var lista = iEmpleadoService.listarPorRol(idRol);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/empresa/{idEmpresa}")
    public ResponseEntity<Set<EmpleadoResponse>> listarEmpleadosPorEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        var lista = iEmpleadoService.listarEmpleadosPorEmpresa(idEmpresa);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> listarPorId(@PathVariable("id") Integer id) throws Exception {
//        var obj = iEmpleadoService.listarPorId(id);
//		
//		if(obj.getIdEmpleado() == null) {
//		    throw new ResourceNotFoundException("Id no encontrado " + id);
//		}

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> registrar(@Valid @RequestBody EmpleadoRequest empleadoRequest) throws Exception {
        System.out.println(empleadoRequest);


        var obj = iEmpleadoService.registrar(empleadoRequest);
        log.info("Empleado creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EmpleadoResponse> modificar(@Valid @RequestBody EmpleadoRequest empleadoRequest) throws Exception {

        var obj = iEmpleadoService.modificar(empleadoRequest);
        log.info("Empleado modificado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
//        Empleado obj = iEmpleadoService.listarPorId(id);
//
//		if(obj.getIdEmpleado() == null) {
//			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
//		}
//
//        iEmpleadoService.eliminar(id);
//        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<EmpleadoResponse>> listarPageable(Pageable pageable) throws Exception {
        var paginas = iEmpleadoService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }
}
