package com.gafahtec.controller;

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

import com.gafahtec.dto.request.MenuRequest;
import com.gafahtec.dto.response.MenuResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.service.IMenuService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/menus")
@AllArgsConstructor
@Log4j2
public class MenuController {

    private IMenuService iMenuService;

    @PostMapping
    public ResponseEntity<MenuResponse> registrar(@Valid @RequestBody MenuRequest menuRequest) throws Exception {

        var obj = iMenuService.registrar(menuRequest);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<MenuResponse>> listarPageable(@PageableDefault(sort = "idMenu") Pageable pageable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception {
        var paginas = iMenuService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> listarPorId(@PathVariable("id") Integer id) throws Exception {
        var obj = iMenuService.listarPorId(id);

        if (obj.getIdMenu() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<Set<MenuResponse>> getMenus() throws Exception {
        var menus = iMenuService.listar();
        if (menus.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iMenuService.listarPorId(id);
        
        if(obj == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }
        iMenuService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping
    public ResponseEntity<MenuResponse> modificar(@Valid @RequestBody MenuRequest menuRequest) throws Exception {

        var obj = iMenuService.modificar(menuRequest);

        log.info("objeto creado " + obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
