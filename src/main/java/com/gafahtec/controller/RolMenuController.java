package com.gafahtec.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.gafahtec.dto.request.RolMenuRequest;
import com.gafahtec.dto.request.RolRequest;
import com.gafahtec.dto.response.RolMenuResponse;
import com.gafahtec.dto.response.RolResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.model.auth.Rol;
import com.gafahtec.service.IRolMenuService;
import com.gafahtec.service.IRolService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/rolMenus")
@AllArgsConstructor
public class RolMenuController {

	private IRolMenuService iRolMenuService;
	
//	@GetMapping
//	public ResponseEntity<Set<RolResponse>> listar() throws Exception{
//		var lista = iRolService.listar();
//		if (lista.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//		return new ResponseEntity<>(lista, HttpStatus.OK);
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Set<RolMenuResponse>> listarPorId(@PathVariable("id") Integer id) throws Exception{
		var obj = iRolMenuService.listarPorId(id);
		System.out.println(obj);
		if(obj == null) {
		    throw new ResourceNotFoundException("idRol no encontrado " + id);
		}
		
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RolMenuResponse> registrar(@Valid @RequestBody RolMenuRequest rolMenuRequest) throws Exception{
		var obj = iRolMenuService.registrar(rolMenuRequest);
		 return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}
	
//	@PutMapping
//	public ResponseEntity<RolResponse> modificar(@Valid @RequestBody RolRequest p) throws Exception{
//		var obj = iRolService.modificar(p);
//		return new ResponseEntity<>(obj, HttpStatus.OK);
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
//		var obj = iRolService.listarPorId(id);
//		
//		if(obj.getIdRol() == null) {
//			throw new ResourceNotFoundException("ID NO ENCONTRADO "+id);
//		}
//			
//		iRolService.eliminar(id);
//		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//	}
//	
	  @GetMapping("/pageable/{idRol}")
      public ResponseEntity<Page<RolMenuResponse>> listarPageable(Pageable pageable,@RequestParam(defaultValue = "0") int page,
              @RequestParam(defaultValue = "5") int size, @PathVariable("idRol") Integer idRol) throws Exception{
		  
		  var paging = PageRequest.of(page, size);
	        var paginas = iRolMenuService.listarPageable(idRol,paging);
	        
          
          if (paginas.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
          return new ResponseEntity<>(paginas, HttpStatus.OK);
      }
}
