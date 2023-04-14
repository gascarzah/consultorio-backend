package com.gafahtec.consultorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.gafahtec.consultorio.dto.request.CitaRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Cita;
import com.gafahtec.consultorio.model.Cliente;
import com.gafahtec.consultorio.model.Horario;
import com.gafahtec.consultorio.model.ProgramacionDetalle;
import com.gafahtec.consultorio.service.ICitaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/citas")
@AllArgsConstructor
public class CitaController {

    private ICitaService iCitaService;

    @GetMapping
    public ResponseEntity<List<Cita>> listar() throws ResourceNotFoundException {
        List<Cita> lista = iCitaService.listar();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Cita>>(lista, HttpStatus.OK);
    }

    @GetMapping("/medico/citados")
    public ResponseEntity<List<Cita>> listaCitados(
            @RequestParam("idMedico") Integer idMedico, @RequestParam("numeroDiaSemana")Integer numeroDiaSemana) throws ResourceNotFoundException {
        List<Cita> lista = new ArrayList<>();

        lista = iCitaService.listaCitados(idMedico, numeroDiaSemana);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Cita>>(lista, HttpStatus.OK);
    }
    

    @GetMapping("/medico")
    public ResponseEntity<List<Cita>> listarCitas(
            @RequestParam("idProgramacionDetalle") Integer idProgramacionDetalle) throws ResourceNotFoundException {
        List<Cita> lista = new ArrayList<>();

        lista = iCitaService.listarCitas(idProgramacionDetalle);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Cita>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> listarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        Cita obj = iCitaService.listarPorId(id);

        if (obj.getIdCita() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<Cita>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cita> registrar(@Valid @RequestBody CitaRequest citaRequest) throws ResourceNotFoundException {

        Cita obj = iCitaService.registrar(Cita.builder()
                .horario(Horario.builder().idHorario(citaRequest.getIdHorario()).build())
                .programacionDetalle(ProgramacionDetalle.builder()
                        .idProgramacionDetalle(citaRequest.getIdProgramacionDetalle()).build())
                .cliente(Cliente.builder().idCliente(citaRequest.getIdCliente()).build())
                .atendido(citaRequest.getAtendido())
                .build());
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cita> modificar(@Valid @RequestBody CitaRequest citaRequest) throws ResourceNotFoundException {
        Cita objRequest = Cita.builder()
                .idCita(citaRequest.getIdCita())
                .cliente(Cliente.builder().idCliente(citaRequest.getIdCliente()).build())
                .programacionDetalle(ProgramacionDetalle.builder().idProgramacionDetalle(citaRequest.getIdProgramacionDetalle()).build())
                .horario(Horario.builder().idHorario(citaRequest.getIdHorario()).build())
                .atendido(citaRequest.getAtendido())
                .informe(citaRequest.getInforme())
                .build();
        Cita obj = iCitaService.modificar(objRequest);
        return new ResponseEntity<Cita>(obj, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        Cita obj = iCitaService.listarPorId(id);

        if (obj == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }
        iCitaService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/historial/pageable")
    public ResponseEntity<Page<Cita>> listarPageableHistorico(@RequestParam("idCliente") Integer idCliente, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) throws ResourceNotFoundException {
        System.out.println("page  "+ page);
        System.out.println("size  "+ size);

        Pageable paging = PageRequest.of(page, size, Sort.by("idCita").descending());
        Page<Cita> paginas  = iCitaService.listaHistorialCitaCliente(idCliente, paging);
        System.out.println("listarPageableCitas paginas "+ paginas);

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Cita>>(paginas, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Cita>> listarPageable(Pageable pageable, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) throws ResourceNotFoundException {
        System.out.println("page  "+ page);
        System.out.println("size  "+ size);

        
        Page<Cita> paginas  = iCitaService.listarPageable( pageable);
        System.out.println("listarPageableCitas paginas "+ paginas);

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Cita>>(paginas, HttpStatus.OK);
    }
}
