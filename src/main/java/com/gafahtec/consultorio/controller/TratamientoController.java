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
import com.gafahtec.consultorio.model.Tratamiento;
import com.gafahtec.consultorio.service.ITratamientoService;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/tratamientos")
@AllArgsConstructor
public class TratamientoController {

	private ITratamientoService iTratamientoService;
	
	@GetMapping
	public ResponseEntity<List<Tratamiento>> listar() throws Exception{
		List<Tratamiento> lista = iTratamientoService.listar();
		return new ResponseEntity<List<Tratamiento>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tratamiento> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Tratamiento obj = iTratamientoService.listarPorId(id);
		
		if(obj.getIdTratamiento() == null) {
			throw new ModeloNotFoundException("Id no encontrado " + id );
		}
		
		return new ResponseEntity<Tratamiento>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Tratamiento> registrar(@Valid @RequestBody Tratamiento p) throws Exception{
		Tratamiento obj = iTratamientoService.registrar(p);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTratamiento()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Tratamiento> modificar(@Valid @RequestBody Tratamiento p) throws Exception{
		Tratamiento obj = iTratamientoService.modificar(p);
		return new ResponseEntity<Tratamiento>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Tratamiento obj = iTratamientoService.listarPorId(id);
		
		if(obj.getIdTratamiento() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iTratamientoService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
