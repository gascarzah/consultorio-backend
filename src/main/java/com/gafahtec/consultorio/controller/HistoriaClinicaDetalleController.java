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
import com.gafahtec.consultorio.model.HistoriaClinicaDetalle;
import com.gafahtec.consultorio.service.IHistoriaClinicaDetalleService;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/historiasClinicaDetalles")
@AllArgsConstructor
public class HistoriaClinicaDetalleController {

	private IHistoriaClinicaDetalleService iHistoriaClinicaDetalleService;
	
	@GetMapping
	public ResponseEntity<List<HistoriaClinicaDetalle>> listar() throws Exception{
		List<HistoriaClinicaDetalle> lista = iHistoriaClinicaDetalleService.listar();
		return new ResponseEntity<List<HistoriaClinicaDetalle>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HistoriaClinicaDetalle> listarPorId(@PathVariable("id") Integer id) throws Exception{
		HistoriaClinicaDetalle obj = iHistoriaClinicaDetalleService.listarPorId(id);
		
		if(obj.getIdHistoriaClinicaDetalle() == null) {
			throw new ModeloNotFoundException("Id no encontrado " + id );
		}
		
		return new ResponseEntity<HistoriaClinicaDetalle>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<HistoriaClinicaDetalle> registrar(@Valid @RequestBody HistoriaClinicaDetalle p) throws Exception{
		HistoriaClinicaDetalle obj = iHistoriaClinicaDetalleService.registrar(p);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdHistoriaClinicaDetalle()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<HistoriaClinicaDetalle> modificar(@Valid @RequestBody HistoriaClinicaDetalle p) throws Exception{
		HistoriaClinicaDetalle obj = iHistoriaClinicaDetalleService.modificar(p);
		return new ResponseEntity<HistoriaClinicaDetalle>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		HistoriaClinicaDetalle obj = iHistoriaClinicaDetalleService.listarPorId(id);
		
		if(obj.getIdHistoriaClinicaDetalle() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iHistoriaClinicaDetalleService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
