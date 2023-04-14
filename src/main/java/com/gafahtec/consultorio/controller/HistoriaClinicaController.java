package com.gafahtec.consultorio.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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

import com.gafahtec.consultorio.dto.request.HistoriaClinicaRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Cliente;
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
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<List<HistoriaClinica>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HistoriaClinica> listarPorId(@PathVariable("id") Integer id) throws Exception{
		HistoriaClinica obj = iHistoriaClinicaService.listarPorId(id);
		
		if(obj.getIdHistoriaClinica() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + id);
		}
		
		return new ResponseEntity<HistoriaClinica>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<HistoriaClinica> registrar(@Valid @RequestBody HistoriaClinicaRequest historiaClinicaRequest) throws Exception{
		HistoriaClinica historiaClinica = new HistoriaClinica();
		BeanUtils.copyProperties(historiaClinica, historiaClinicaRequest);
		historiaClinica.setCliente(Cliente.builder().idCliente(historiaClinicaRequest.getIdCliente()).build());
		HistoriaClinica obj = iHistoriaClinicaService.registrar(historiaClinica);
		 return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<HistoriaClinica> modificar(@Valid @RequestBody HistoriaClinicaRequest historiaClinicaRequest) throws Exception{
		HistoriaClinica historiaClinica = new HistoriaClinica();
		BeanUtils.copyProperties(historiaClinica, historiaClinicaRequest);
		historiaClinica.setCliente(Cliente.builder().idCliente(historiaClinicaRequest.getIdCliente()).build());
		HistoriaClinica obj = iHistoriaClinicaService.modificar(historiaClinica);
		return new ResponseEntity<HistoriaClinica>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		HistoriaClinica obj = iHistoriaClinicaService.listarPorId(id);
		
		if(obj.getIdHistoriaClinica() == null) {
			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iHistoriaClinicaService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
//  @GetMapping("/pageable")
//  public ResponseEntity<Page<Empresa>> listarPageable(@PageableDefault(sort = "apellidoPaterno")Pageable pageable,@RequestParam(defaultValue = "0") int page,
//          @RequestParam(defaultValue = "5") int size) throws Exception{
//      Page<Empresa> paginas = iEmpresaService.listarPageable(pageable);
//      if (paginas.isEmpty()) {
//          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//      return new ResponseEntity<Page<Empresa>>(paginas, HttpStatus.OK);
//  }
}
