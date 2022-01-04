package com.gafahtec.consultorio.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gafahtec.consultorio.exception.ModeloNotFoundException;
import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.service.IProgramacionService;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/programaciones")
@AllArgsConstructor
public class ProgramacionController {

	private IProgramacionService iProgramacionService;
	
	@GetMapping
	public ResponseEntity<List<Programacion>> listar() throws Exception{
		List<Programacion> lista = iProgramacionService.listar();
		return new ResponseEntity<List<Programacion>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Programacion> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Programacion obj = iProgramacionService.listarPorId(id);
		
		if(obj.getIdProgramacion() == null) {
			throw new ModeloNotFoundException("Id no encontrado " + id );
		}
		
		return new ResponseEntity<Programacion>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Programacion> registrar(@Valid @RequestBody Programacion p) throws Exception{
		Programacion obj = iProgramacionService.registrar(p);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdProgramacion()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Programacion> modificar(@Valid @RequestBody Programacion p) throws Exception{
		Programacion obj = iProgramacionService.modificar(p);
		return new ResponseEntity<Programacion>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Programacion obj = iProgramacionService.listarPorId(id);
		
		if(obj.getIdProgramacion() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iProgramacionService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
