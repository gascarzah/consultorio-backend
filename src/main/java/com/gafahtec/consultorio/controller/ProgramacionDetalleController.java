package com.gafahtec.consultorio.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.gafahtec.consultorio.dto.request.ProgramacionDetalleRequest;
import com.gafahtec.consultorio.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.model.ProgramacionDetalle;
import com.gafahtec.consultorio.service.IProgramacionDetalleService;
import com.gafahtec.consultorio.service.IProgramacionService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/programacionesDetalladas")
@AllArgsConstructor
@Log4j2
public class ProgramacionDetalleController {

    private IProgramacionDetalleService iProgramacionDetalleService;

    private IProgramacionService iProgramacionService;

    @GetMapping("/medico")
    public ResponseEntity<List<ProgramacionDetalle>> listarPorMedico(@RequestParam("idMedico") Integer idMedico)
            throws Exception {
        List<ProgramacionDetalle> lista = new ArrayList<>();

        lista = iProgramacionDetalleService.generarDiasProgramados(idMedico);

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ProgramacionDetalle>>(lista, HttpStatus.OK);
    }

    @GetMapping("/medico/dia")
    public ResponseEntity<List<ProgramacionDetalle>> citasPendientes(@RequestParam("idMedico") Integer idMedico,
            @RequestParam("numeroDiaSemana") Integer numeroDiaSemana)
            throws Exception {
        List<ProgramacionDetalle> lista = new ArrayList<>();

        lista = iProgramacionDetalleService.citasPendientes(idMedico, numeroDiaSemana);

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ProgramacionDetalle>>(lista, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<ProgramacionDetalle>> listarPageable( @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception {
        
        Pageable paging = PageRequest.of(page, size, Sort.by("numeroDiaSemana").descending());
        Page<ProgramacionDetalle> paginas  = iProgramacionDetalleService.listarProgramacionEmpleadoPageable( paging);
        
//        Page<ProgramacionDetalle> paginas = iProgramacionDetalleService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<ProgramacionDetalle>>(paginas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProgramacionDetalle> registrar(
            @Valid @RequestBody ProgramacionDetalleRequest programacionDetalleRequest) throws Exception {
        Programacion programacion = iProgramacionService.listarPorId(programacionDetalleRequest.getIdProgramacion());
        iProgramacionDetalleService.generarProgramacionDetalle(programacion, programacionDetalleRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/programaDetalle/{id}")
    public ResponseEntity<ProgramacionDetalleResponse> listarPorId(@PathVariable("id") Integer idProgramacion)
            throws Exception {

        ProgramacionDetalleResponse obj = iProgramacionDetalleService.listarPorIdProgramacion(idProgramacion);

        if (obj.getEmpleado() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + idProgramacion);
        }

        return new ResponseEntity<ProgramacionDetalleResponse>(obj, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProgramacionDetalle> modificar(
            @Valid @RequestBody ProgramacionDetalleRequest programacionDetalleRequest) throws Exception {

        System.out.println(programacionDetalleRequest);
        List<ProgramacionDetalle> lista = iProgramacionDetalleService
                .listarPorProgramacion(programacionDetalleRequest.getIdProgramacion());
        
        //nuevos
        List<String> listaAgregar = new ArrayList<>(Arrays.asList(programacionDetalleRequest.getChecked()));
        List<ProgramacionDetalle> listProgramacionDetalleDelete = new ArrayList<>();
        for(int i= 0; i < lista.size(); i ++) {
            ProgramacionDetalle pd = lista.get(i);
            listProgramacionDetalleDelete.add(pd);
            for(int x=0; x<listaAgregar.size();x++) {
                String numero = listaAgregar.get(x);
                if(StringUtils.isNotEmpty(numero)) {
                    if(pd.getNumeroDiaSemana() -1 == Integer.parseInt(numero)) {
                        listProgramacionDetalleDelete.remove(pd);
                        listaAgregar.remove(numero);
                        break;
                    }   
                    
                }
            
            }
        }
        
        System.out.println("listaTemporal "+listaAgregar);
        System.out.println("listProgramacionDetalleTemp "+listProgramacionDetalleDelete);
        if(!listaAgregar.isEmpty()) {
        programacionDetalleRequest.setChecked(Arrays.copyOf(listaAgregar.toArray(), listaAgregar.size(), String[].class) );
       
        Programacion programacion = iProgramacionService.listarPorId(programacionDetalleRequest.getIdProgramacion());
        
        iProgramacionDetalleService.generarProgramacionDetalle(programacion, 
                programacionDetalleRequest);
        }
        
        for(ProgramacionDetalle pgdel : listProgramacionDetalleDelete) {
            
            iProgramacionDetalleService.eliminar(pgdel.getIdProgramacionDetalle());
        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iProgramacionDetalleService.listarPorId(id);

        if (obj.getIdProgramacionDetalle()== null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }

        iProgramacionService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
