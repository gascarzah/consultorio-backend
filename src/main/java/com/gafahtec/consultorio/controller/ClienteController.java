package com.gafahtec.consultorio.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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

import com.gafahtec.consultorio.dto.request.ClienteRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Cliente;
import com.gafahtec.consultorio.model.HistoriaClinica;
import com.gafahtec.consultorio.service.IClienteService;
import com.gafahtec.consultorio.service.IHistoriaClinicaService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
@Log4j2
public class ClienteController {

	private IClienteService iClienteService;
	private IHistoriaClinicaService iHistoriaClinicaService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() throws Exception{
		List<Cliente> lista = iClienteService.listar();
		if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<List<Cliente>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Cliente obj = iClienteService.listarPorId(id);
		
		if(obj.getIdCliente() == null) {
		    throw new ResourceNotFoundException("Id no encontrado " + id);
		}
		
		return new ResponseEntity<Cliente>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> registrar(@Valid @RequestBody ClienteRequest clienteRequest) throws Exception{
		Cliente cliente = new Cliente();
		BeanUtils.copyProperties(cliente, clienteRequest);
		Cliente objCliente = iClienteService.registrar(cliente);
		iHistoriaClinicaService.registrar(HistoriaClinica.builder()
		                                                 .cliente(objCliente)
		                                                 .build())
		                                                 ;
		log.info("objeto creado "+ objCliente);
		 return new ResponseEntity<>(objCliente, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Cliente> modificar(@Valid @RequestBody ClienteRequest clienteRequest) throws Exception{
		Cliente cliente = new Cliente();
		BeanUtils.copyProperties(cliente, clienteRequest);
		Cliente obj = iClienteService.modificar(cliente);
		log.info("objeto modificado "+ obj);
		return new ResponseEntity<Cliente>(obj, HttpStatus.OK);
	}
	

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iClienteService.listarPorId(id);
        
        if(obj == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }
        iClienteService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
	

	@GetMapping("/pageable")
	public ResponseEntity<Page<Cliente>> listarPageable(@PageableDefault(sort = "apellidoPaterno")Pageable pageable,@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception{
		Page<Cliente> paginas = iClienteService.listarPageable(pageable);
		if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
		return new ResponseEntity<Page<Cliente>>(paginas, HttpStatus.OK);
	}
}
