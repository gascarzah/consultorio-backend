package com.gafahtec.consultorio.controller;

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

import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.auth.Usuario;
import com.gafahtec.consultorio.service.IUsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Usuario", description = "Operaciones sobre usuarios")
public class UsuarioController {

    private final IUsuarioService iUsuarioService;

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Usuario> register(
            @RequestBody UsuarioRequest request) {

        var obj = iUsuarioService.register(request);

        if (obj == null) {
            throw new ResourceNotFoundException("Correo ya se encuentra registrado");

        }
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @Operation(summary = "Modificar usuario", description = "Modifica un usuario existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario modificado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping
    public ResponseEntity<Usuario> modificar(@RequestBody UsuarioRequest usuarioRequest) throws Exception {
        Usuario obj = iUsuarioService.modificarUsuario(usuarioRequest);

        if (obj == null) {
            throw new ResourceNotFoundException("Problemas");

        }

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Operation(summary = "Obtener usuario por email", description = "Obtiene un usuario por su email.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{email}")
    public ResponseEntity<Usuario> getUsuarioPorEmail(@PathVariable("email") String email) throws Exception {
        var obj = iUsuarioService.findByEmail(email);
        log.info("===> obtien " + obj);
        if (obj.isEmpty()) {
            throw new RuntimeException("Email no encontrado " + email);
        }

        return new ResponseEntity<Usuario>(obj.get(), HttpStatus.OK);
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws
    // Exception {
    // Usuario obj = iUsuarioService.listarPorId(id);
    //
    // if (obj.getEmail() == null) {
    // throw new RuntimeException("ID NO ENCONTRADO " + id);
    // }
    //
    // iUsuarioService.eliminar(id);
    // return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    // }

    @Operation(summary = "Listar usuarios paginados", description = "Obtiene usuarios paginados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/pageable")
    public ResponseEntity<Page<Usuario>> listarPageable(@PageableDefault(sort = "email") Pageable pageable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws Exception {
        Page<Usuario> paginas = iUsuarioService.listarPageable(pageable);
        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Usuario>>(paginas, HttpStatus.OK);
    }

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario por su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable("id") Integer id) throws Exception {
        Usuario obj = iUsuarioService.getUsuarioPorId(id);

        if (obj.getEmail() == null) {
            throw new RuntimeException("Email no encontrado " + id);
        }

        return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
    }

}
