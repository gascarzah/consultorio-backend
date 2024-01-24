package com.gafahtec.controller;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.gafahtec.dto.request.CitaRequest;
import com.gafahtec.dto.response.CitaResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.service.ICitaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/citas")
@AllArgsConstructor
public class CitaController {

    private ICitaService iCitaService;

    @GetMapping
    public ResponseEntity<Set<CitaResponse>> listar() throws ResourceNotFoundException {
    	var lista = iCitaService.listar();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/listaCitados")
    public ResponseEntity<Set<CitaResponse>> listaCitados(
            @RequestParam("idEmpresa") Integer idEmpresa, 
            @RequestParam("numeroDocumento") String numeroDocumento,
            @RequestParam("numeroDiaSemana")Integer numeroDiaSemana) throws ResourceNotFoundException {
       System.out.println("idEmpresa "+  idEmpresa); 
       System.out.println("numeroDocumento "+  numeroDocumento); 
       System.out.println("numeroDiaSemana "+  numeroDiaSemana); 
           
    	var lista = iCitaService.listaCitados(idEmpresa,numeroDocumento, numeroDiaSemana);
    	System.out.println("lista "+  lista); 
    	if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    

    @GetMapping("/medico")
    public ResponseEntity<Set<CitaResponse>> listarCitas(
            @RequestParam("idProgramacionDetalle") Integer idProgramacionDetalle) throws ResourceNotFoundException {
        var lista = iCitaService.listarCitas(idProgramacionDetalle);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> listarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        var obj = iCitaService.listarPorId(id);

        if (obj.getIdCita() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CitaResponse> registrar(@Valid @RequestBody CitaRequest citaRequest) throws ResourceNotFoundException {
    	var obj =iCitaService.registrar(citaRequest);
       
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CitaResponse> modificar(@Valid @RequestBody CitaRequest citaRequest) throws ResourceNotFoundException {
       
        var obj = iCitaService.modificar(citaRequest);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iCitaService.listarPorId(id);

        if (obj == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }
        iCitaService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/historial/pageable")
    public ResponseEntity<Page<CitaResponse>> listarPageableHistorico(
    		@RequestParam("numeroDocumento") String numeroDocumento, 
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) throws ResourceNotFoundException {
    	System.out.println("numeroDocumento  "+ numeroDocumento);
        System.out.println("page  "+ page);
        System.out.println("size  "+ size);

        Pageable paging = PageRequest.of(page, size);
        Page<CitaResponse> paginas  = iCitaService.listaHistorialCitaCliente(numeroDocumento, paging);
        System.out.println("listarPageableCitas paginas "+ paginas);

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<CitaResponse>> listarPageable(Pageable pageable, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) throws ResourceNotFoundException {
        System.out.println("page  "+ page);
        System.out.println("size  "+ size);

        
        Page<CitaResponse> paginas  = iCitaService.listarPageable( pageable);
        System.out.println("listarPageableCitas paginas "+ paginas);

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }
}
