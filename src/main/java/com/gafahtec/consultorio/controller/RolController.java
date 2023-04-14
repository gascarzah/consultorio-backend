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
import com.gafahtec.consultorio.model.Rol;
import com.gafahtec.consultorio.service.IRolService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RolController {

	private IRolService iRolService;
	
	@GetMapping
	public ResponseEntity<List<Rol>> listar() throws Exception{
		List<Rol> lista = iRolService.listar();
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<List<Rol>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rol> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Rol obj = iRolService.listarPorId(id);
		
		if(obj.getIdRol() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + id);
		}
		
		return new ResponseEntity<Rol>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Rol> registrar(@Valid @RequestBody Rol p) throws Exception{
		Rol obj = iRolService.registrar(p);
		 return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Rol> modificar(@Valid @RequestBody Rol p) throws Exception{
		Rol obj = iRolService.modificar(p);
		return new ResponseEntity<Rol>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Rol obj = iRolService.listarPorId(id);
		
		if(obj.getIdRol() == null) {
			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iRolService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	  @GetMapping("/pageable")
      public ResponseEntity<Page<Rol>> listarPageable(@PageableDefault(sort = "nombre")Pageable pageable,@RequestParam(defaultValue = "0") int page,
              @RequestParam(defaultValue = "5") int size) throws Exception{
          Page<Rol> paginas = iRolService.listarPageable(pageable);
          if (paginas.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<Page<Rol>>(paginas, HttpStatus.OK);
      }
}
