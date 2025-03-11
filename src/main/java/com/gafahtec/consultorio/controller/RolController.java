package com.gafahtec.consultorio.controller;

import java.util.List;
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

import com.gafahtec.consultorio.dto.request.RolRequest;
import com.gafahtec.consultorio.dto.response.RolResponse;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.auth.Rol;
import com.gafahtec.consultorio.service.IRolService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RolController {

	private IRolService iRolService;
	
	@GetMapping
	public ResponseEntity<Set<RolResponse>> listar() throws Exception{
		var lista = iRolService.listar();
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RolResponse> listarPorId(@PathVariable("id") Integer id) throws Exception{
		var obj = iRolService.listarPorId(id);
		
		if(obj.getIdRol() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + id);
		}
		
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RolResponse> registrar(@Valid @RequestBody RolRequest p) throws Exception{
		var obj = iRolService.registrar(p);
		 return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<RolResponse> modificar(@Valid @RequestBody RolRequest p) throws Exception{
		var obj = iRolService.modificar(p);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		var obj = iRolService.listarPorId(id);
		
		if(obj.getIdRol() == null) {
			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
		}
			
		iRolService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	  @GetMapping("/pageable")
      public ResponseEntity<Page<RolResponse>> listarPageable(@PageableDefault(sort = "nombre")Pageable pageable,@RequestParam(defaultValue = "0") int page,
              @RequestParam(defaultValue = "5") int size) throws Exception{
          var paginas = iRolService.listarPageable(pageable);
          if (paginas.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<>(paginas, HttpStatus.OK);
      }
}
