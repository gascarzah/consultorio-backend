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
import com.gafahtec.consultorio.model.HistoriaClinica;
import com.gafahtec.consultorio.service.IHistoriaClinicaService;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/historiasClinicas")
@AllArgsConstructor
public class HistoriaClinicaController {

	private IHistoriaClinicaService iHistoriaClinicaService;
	
	@GetMapping
	public ResponseEntity<List<HistoriaClinica>> listar() throws Exception{
		List<HistoriaClinica> lista = iHistoriaClinicaService.listar();
		return new ResponseEntity<List<HistoriaClinica>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HistoriaClinica> listarPorId(@PathVariable("id") Integer id) throws Exception{
		HistoriaClinica obj = iHistoriaClinicaService.listarPorId(id);
		
		if(obj.getIdHistoriaClinica() == null) {
			throw new ModeloNotFoundException("Id no encontrado " + id );
		}
		
		return new ResponseEntity<HistoriaClinica>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<HistoriaClinica> registrar(@Valid @RequestBody HistoriaClinica p) throws Exception{
		HistoriaClinica obj = iHistoriaClinicaService.registrar(p);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdHistoriaClinica()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<HistoriaClinica> modificar(@Valid @RequestBody HistoriaClinica p) throws Exception{
		HistoriaClinica obj = iHistoriaClinicaService.modificar(p);
		return new ResponseEntity<HistoriaClinica>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		HistoriaClinica obj = iHistoriaClinicaService.listarPorId(id);
		
		if(obj.getIdHistoriaClinica() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iHistoriaClinicaService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
