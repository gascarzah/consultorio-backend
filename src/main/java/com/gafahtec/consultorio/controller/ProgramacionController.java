package com.gafahtec.consultorio.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.gafahtec.consultorio.dto.request.ProgramacionRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.service.IProgramacionService;
import com.gafahtec.consultorio.util.Constants;
import com.gafahtec.consultorio.util.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/programaciones")
@AllArgsConstructor
@Log4j2
public class ProgramacionController {

    private IProgramacionService iProgramacionService;



    @GetMapping
    public ResponseEntity<List<Programacion>> listar() throws Exception {
        List<Programacion> lista = iProgramacionService.listar();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Programacion>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Programacion> listarPorId(@PathVariable("id") Integer id) throws Exception {
        Programacion obj = iProgramacionService.listarPorId(id);

        if (obj.getIdProgramacion() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<Programacion>(obj, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Programacion> programacionEstado(@PathVariable("estado") Boolean estado) throws Exception {
        List<Programacion> list = iProgramacionService.programacionEstado(estado);
        Programacion obj = null;
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("estado no encontrado " + estado);
        }else {
           obj = list.get(0); 
        }

        return new ResponseEntity<Programacion>(obj, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Programacion> registrar(@Valid @RequestBody ProgramacionRequest programacionRequest)
            throws Exception {

        String strFechaInicial = Utils.getFecha2String(programacionRequest.getFechaInicial());
        String strFechaFinal = Utils.getFecha2String(programacionRequest.getFechaFinal());

        String rango = strFechaInicial + " - " + strFechaFinal;

        Programacion programacion = new Programacion();
        BeanUtils.copyProperties(programacion, programacionRequest);
        programacion.setRango(rango);
        programacion.setEstado(Constants.ACTIVO);
        programacion.setStrFechaFinal(strFechaFinal);
        programacion.setStrFechaInicial(strFechaInicial);
        Programacion obj = iProgramacionService.registrar(programacion);

        return new ResponseEntity<>(obj, HttpStatus.CREATED);

    }

//	@PostMapping
//	public ResponseEntity<Programacion> registrar(@Valid @RequestBody ProgramacionRequest programacionRequest) throws Exception{
//
//	    String strFechaInicial = Utils.getFecha2String(programacionRequest.getFechaInicial());
//        String strFechaFinal = Utils.getFecha2String(programacionRequest.getFechaFinal());
//	    
//		String rango = strFechaInicial + " - " + strFechaFinal;
//		List<Programacion> programacionList = iProgramacionService.listarPorRango(rango);
//		Programacion obj = null;
//		if(programacionList.isEmpty()) {
//			Programacion programacion = new Programacion();
//			BeanUtils.copyProperties(programacion, programacionRequest);
//			programacion.setRango(rango);
//			programacion.setEstado(0);
//			programacion.setStrFechaFinal(strFechaFinal);
//			programacion.setStrFechaInicial(strFechaInicial);
//			obj = iProgramacionService.registrar(programacion);
//		}else{
//			obj = programacionList.get(0);
//		}
//
//
//			log.info("resultado : ", obj);
//			if (obj != null) {
//			    
//			    List<ProgramacionDetalle> existeProgramacionMedico = iProgramacionDetalleService.getProgramacionMedico(obj.getIdProgramacion(), programacionRequest.getIdEmpleado());
//			    
//			    if(existeProgramacionMedico.isEmpty()) {
//			    
//			    List<ProgramacionDetalle> list = iProgramacionDetalleService.generarProgramacionDetalle(obj, programacionRequest);
//			    
//			    iCitaService.registrarHorarios(list);
//			   
//			    
//				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdProgramacion()).toUri();
//				return ResponseEntity.created(location).build();
//			    }else {
//			        log.info("Ya existe programacion vinculada a esta semana");
//			        throw new ResourceNotFoundException("Ya existe programacion vinculada a esta semana");
//			    }
//			}
//			 return new ResponseEntity<>(obj, HttpStatus.CREATED);
//
//
//
//	}

    @PutMapping
    public ResponseEntity<Programacion> modificar(@Valid @RequestBody Programacion p) throws Exception {
        Programacion obj = iProgramacionService.modificar(p);
        return new ResponseEntity<Programacion>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        Programacion obj = iProgramacionService.listarPorId(id);

        if (obj.getIdProgramacion() == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }

        iProgramacionService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{idEmpresa}/pageable")
    public ResponseEntity<Page<Programacion>> listarPageable(
            @PathVariable("idEmpresa") Integer idEmpresa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception {
        
        Pageable paging = PageRequest.of(page, size, Sort.by("idProgramacion").descending());
        Page<Programacion> paginas = iProgramacionService.listarProgramacionPageable(idEmpresa,paging);
        
//        Page<Programacion> paginas = iProgramacionService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Programacion>>(paginas, HttpStatus.OK);
    }
}
