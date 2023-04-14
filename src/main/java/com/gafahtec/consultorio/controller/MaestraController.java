package com.gafahtec.consultorio.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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

import com.gafahtec.consultorio.dto.request.MaestraRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Maestra;
import com.gafahtec.consultorio.service.IMaestraService;
import com.gafahtec.consultorio.util.Constants;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/maestras")
@AllArgsConstructor
@Log4j2
public class MaestraController {

    private IMaestraService iMaestraService;

    @PostMapping
    public ResponseEntity<Maestra> registrar(@Valid @RequestBody MaestraRequest MaestraRequest) throws Exception {
        Maestra maestra = Maestra.builder().build();
        BeanUtils.copyProperties(maestra, MaestraRequest);
        maestra.setEstado(Constants.ACTIVO);
        Maestra obj = iMaestraService.registrar(maestra);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Maestra>> listarPageable(Pageable pageable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam("idEmpresa") Integer idEmpresa,
            @RequestParam("idMaestra") Integer idMaestra) throws Exception {
        Pageable paging = PageRequest.of(page, size, Sort.by("idMaestra").descending());
//        Page<Maestra> paginas = iMaestraService.listarPageable(idEmpresa,descripcion, pageable);
        Page<Maestra> paginas  = iMaestraService.listarMaestraPageable(idEmpresa,idMaestra, paging);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Maestra>>(paginas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maestra> listarPorId(@PathVariable("id") Integer id) throws Exception {
        Maestra obj = iMaestraService.listarPorId(id);

        if (obj.getIdMaestra() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<Maestra>(obj, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Maestra>> getMaestras() throws Exception {
        List<Maestra> Maestras = iMaestraService.listar();
        if (Maestras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Maestra>>(Maestras, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iMaestraService.listarPorId(id);
        
        if(obj == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }
        iMaestraService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping
    public ResponseEntity<Maestra> modificar(@Valid @RequestBody MaestraRequest MaestraRequest) throws Exception {
        Maestra maestra = Maestra.builder().build();
        BeanUtils.copyProperties(maestra, MaestraRequest);
        Maestra obj = iMaestraService.modificar(maestra);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
