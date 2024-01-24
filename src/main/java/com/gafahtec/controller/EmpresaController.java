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

import com.gafahtec.dto.request.EmpresaRequest;
import com.gafahtec.dto.response.EmpresaResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.service.IEmpresaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/empresas")
@AllArgsConstructor
public class EmpresaController {

	private IEmpresaService iEmpresaService;
	
	@GetMapping
	public ResponseEntity<Set<EmpresaResponse>> listar() throws Exception{
		var lista = iEmpresaService.listar();
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmpresaResponse> listarPorId(@PathVariable("id") Integer id) throws Exception{
		var obj = iEmpresaService.listarPorId(id);
		
		if(obj.getIdEmpresa() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + id);
		}
		
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<EmpresaResponse> registrar(@Valid @RequestBody EmpresaRequest empresaRequest) throws Exception{
		var obj = iEmpresaService.registrar(empresaRequest);
		 return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<EmpresaResponse> modificar(@Valid @RequestBody EmpresaRequest empresaRequest) throws Exception{
		var obj = iEmpresaService.modificar(empresaRequest);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		var obj = iEmpresaService.listarPorId(id);
		
		if(obj.getIdEmpresa() == null) {
			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iEmpresaService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	   @GetMapping("/pageable")
	    public ResponseEntity<Page<EmpresaResponse>> listarPageable(@PageableDefault(sort = "nombre")Pageable pageable,@RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size) throws Exception{
	        var paginas = iEmpresaService.listarPageable(pageable);
	        if (paginas.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(paginas, HttpStatus.OK);
	    }
}
