package com.gafahtec.consultorio.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gafahtec.consultorio.dto.request.EmpleadoRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Empleado;
import com.gafahtec.consultorio.model.Empresa;
import com.gafahtec.consultorio.model.Persona;
import com.gafahtec.consultorio.model.TipoEmpleado;
import com.gafahtec.consultorio.service.IEmpleadoService;
import com.gafahtec.consultorio.service.IPersonaService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/empleados")
@AllArgsConstructor
@Log4j2
public class EmpleadoController {

    private IEmpleadoService iEmpleadoService;
    private IPersonaService iPersonaService;
    
    @GetMapping(value = "/medicos/{idRol}")
    public ResponseEntity<List<Empleado>> listar(@PathVariable("idRol") Integer idRol) throws Exception {
        List<Empleado> lista = iEmpleadoService.listarPorRol(idRol);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Empleado>>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/tipoEmpleado/{idEmpresa}/{descTipoEmpleado}")
    public ResponseEntity<List<Empleado>> listarPorTipoEmpleado(@PathVariable("idEmpresa") Integer idEmpresa,
            @PathVariable("descTipoEmpleado") String descTipoEmpleado) throws Exception {
        List<Empleado> lista = iEmpleadoService.listarPorTipoEmpleado(idEmpresa, descTipoEmpleado);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Empleado>>(lista, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> listarPorId(@PathVariable("id") Integer id) throws Exception {
        Empleado obj = iEmpleadoService.listarPorId(id);
//		
//		if(obj.getIdEmpleado() == null) {
//		    throw new ResourceNotFoundException("Id no encontrado " + id);
//		}

        return new ResponseEntity<Empleado>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Empleado> registrar(@Valid @RequestBody EmpleadoRequest empleadoRequest) throws Exception {
        System.out.println(empleadoRequest);

        var savedPersona = iPersonaService.registrar(Persona.builder()
                .numeroDocumento(empleadoRequest.getNumeroDocumento())
                .apellidoMaterno(empleadoRequest.getApellidoMaterno())
                .apellidoPaterno(empleadoRequest.getApellidoPaterno())
                .nombres(empleadoRequest.getNombres())
                .direccion(empleadoRequest.getDireccion())
                .build());
                

        
        var empleado = Empleado.builder()
                .empresa(Empresa.builder().idEmpresa(empleadoRequest.getIdEmpresa()).build())
                .persona(savedPersona)
                .tipoEmpleado(TipoEmpleado.builder()
                        .idTipoEmpleado(empleadoRequest.getIdTipoEmpleado())
                        .build())
                .build();
        System.out.println(empleado);
        Empleado obj = iEmpleadoService.registrar(empleado);
        log.info("Empleado creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Empleado> modificar(@Valid @RequestBody EmpleadoRequest EmpleadoRequest) throws Exception {
        Empleado Empleado = new Empleado();
        BeanUtils.copyProperties(Empleado, EmpleadoRequest);
        Empleado obj = iEmpleadoService.modificar(Empleado);
        log.info("Empleado modificado " + obj);
        return new ResponseEntity<Empleado>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        Empleado obj = iEmpleadoService.listarPorId(id);

		if(obj.getIdEmpleado() == null) {
			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
		}

        iEmpleadoService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Empleado>> listarPageable(Pageable pageable) throws Exception {
        Page<Empleado> paginas = iEmpleadoService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Empleado>>(paginas, HttpStatus.OK);
    }
}
