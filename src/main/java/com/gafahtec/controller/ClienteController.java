package com.gafahtec.controller;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gafahtec.dto.request.ClienteRequest;
import com.gafahtec.dto.response.ClienteResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.service.IClienteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
@Log4j2
public class ClienteController {

	private IClienteService iClienteService;
	
	@GetMapping
	public ResponseEntity<Set<ClienteResponse>> listar() throws Exception{
		var lista = iClienteService.listar();
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{numeroDocumento}")
	public ResponseEntity<ClienteResponse> listarPorId(@PathVariable("numeroDocumento") String numeroDocumento) throws Exception{
		var obj = iClienteService.listarPorId(numeroDocumento);
		
		if(obj.getNumeroDocumento() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + numeroDocumento);
		}
		
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ClienteResponse> registrar(@Valid @RequestBody ClienteRequest clienteRequest) throws Exception{
		var cliente =iClienteService.registrarConHistoriaClinica(clienteRequest);
		
		
		                                                 ;
		log.info("objeto creado "+ cliente);
		 return new ResponseEntity<>(cliente, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<ClienteResponse> modificar(@Valid @RequestBody ClienteRequest clienteRequest) throws Exception{

		var obj = iClienteService.modificar(clienteRequest);
		log.info("objeto modificado "+ obj);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> eliminar(@PathVariable("numeroDocumento") String numeroDocumento) throws Exception {
//        var obj = iClienteService.listarPorId(numeroDocumento);
//        
//        if(obj == null) {
//            throw new ResourceNotFoundException("ID NO ENCONTRADO " + numeroDocumento);
//        }
//        iClienteService.eliminar(numeroDocumento);
//        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//    }
	

	@GetMapping("/pageable")
	public ResponseEntity<Page<ClienteResponse>> listarPageable(Pageable pageable,@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception{
		var paginas = iClienteService.listarPageable(pageable);
		if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<>(paginas, HttpStatus.OK);
	}
}
