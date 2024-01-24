package com.gafahtec.controller;

import java.util.Set;

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

import com.gafahtec.dto.request.HistoriaClinicaRequest;
import com.gafahtec.dto.response.HistoriaClinicaResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.service.IHistoriaClinicaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/historiasClinicas")
@AllArgsConstructor
public class HistoriaClinicaController {

	private IHistoriaClinicaService iHistoriaClinicaService;

	@GetMapping
	public ResponseEntity<Set<HistoriaClinicaResponse>> listar() throws Exception{
		var lista = iHistoriaClinicaService.listar();
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HistoriaClinicaResponse> listarPorId(@PathVariable("id") Integer id) throws Exception{
		var obj = iHistoriaClinicaService.listarPorId(id);
		
		if(obj.getNumeroHistoriaClinica() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + id);
		}
		
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<HistoriaClinicaResponse> registrar(@Valid @RequestBody HistoriaClinicaRequest historiaClinicaRequest) throws Exception{

		var obj = iHistoriaClinicaService.registrar(historiaClinicaRequest);
		 return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<HistoriaClinicaResponse> modificar(@Valid @RequestBody HistoriaClinicaRequest historiaClinicaRequest) throws Exception{

		var obj = iHistoriaClinicaService.modificar(historiaClinicaRequest);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		var obj = iHistoriaClinicaService.listarPorId(id);
		
		if(obj.getNumeroHistoriaClinica() == null) {
			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iHistoriaClinicaService.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
  @GetMapping("/pageable")
  public ResponseEntity<Page<HistoriaClinicaResponse>> listarPageable(Pageable pageable,@RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size) throws Exception{
      Page<HistoriaClinicaResponse> paginas = iHistoriaClinicaService.listarPageable(pageable);
      if (paginas.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<Page<HistoriaClinicaResponse>>(paginas, HttpStatus.OK);
  }
}
