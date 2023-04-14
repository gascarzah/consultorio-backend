package com.gafahtec.consultorio.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Cliente;
import com.gafahtec.consultorio.model.Empresa;
import com.gafahtec.consultorio.service.IEmpresaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/empresas")
@AllArgsConstructor
public class EmpresaController {

	private IEmpresaService iEmpresaService;
	
	@GetMapping
	public ResponseEntity<List<Empresa>> listar() throws Exception{
		List<Empresa> lista = iEmpresaService.listar();
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<List<Empresa>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Empresa> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Empresa obj = iEmpresaService.listarPorId(id);
		
		if(obj.getIdEmpresa() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + id);
		}
		
		return new ResponseEntity<Empresa>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Empresa> registrar(@Valid @RequestBody Empresa p) throws Exception{
		Empresa obj = iEmpresaService.registrar(p);
		 return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Empresa> modificar(@Valid @RequestBody Empresa p) throws Exception{
		Empresa obj = iEmpresaService.modificar(p);
		return new ResponseEntity<Empresa>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Empresa obj = iEmpresaService.listarPorId(id);
		
		if(obj.getIdEmpresa() == null) {
			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iEmpresaService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	   @GetMapping("/pageable")
	    public ResponseEntity<Page<Empresa>> listarPageable(@PageableDefault(sort = "nombre")Pageable pageable,@RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size) throws Exception{
	        Page<Empresa> paginas = iEmpresaService.listarPageable(pageable);
	        if (paginas.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<Page<Empresa>>(paginas, HttpStatus.OK);
	    }
}
