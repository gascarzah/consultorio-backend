package com.gafahtec.consultorio.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Rol;
import com.gafahtec.consultorio.model.Usuario;
import com.gafahtec.consultorio.service.UsuarioService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@Log4j2
public class UsuarioController {

    private UsuarioService iUsuarioService;

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> getUsuarioPorEmail(@PathVariable("email") String email) throws Exception {
        Usuario obj = iUsuarioService.getUsuario(email);

        if (obj.getEmail() == null) {
            throw new RuntimeException("Email no encontrado " + email);
        }

        return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> registrar(@RequestBody UsuarioRequest usuarioRequest) {

        Usuario obj = iUsuarioService.registrarUsuarioEmpleado(usuarioRequest);
        if (obj == null) {
            throw new ResourceNotFoundException("Correo ya se encuentra registrado");

        }

        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Usuario> modificar(@RequestBody UsuarioRequest usuarioRequest) throws Exception {
        Usuario obj = iUsuarioService.modificarUsuarioEmpleado(usuarioRequest);
        
        if (obj == null) {
            throw new ResourceNotFoundException("Problemas");

        }
        
        return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        Usuario obj = iUsuarioService.listarPorId(id);

        if (obj.getEmail() == null) {
            throw new RuntimeException("ID NO ENCONTRADO " + id);
        }

        iUsuarioService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Usuario user = iUsuarioService.getUsuario(username);

                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 2000))
//						.withExpiresAt(new Date(System.currentTimeMillis()+ 30000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Rol::getNombre).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                System.out.println(tokens);
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                log.error("Error loggin in {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token perdido");

        }
    }
    
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

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable("id") Integer id) throws Exception {
        Usuario obj = iUsuarioService.getUsuarioPorId(id);

        if (obj.getEmail() == null) {
            throw new RuntimeException("Email no encontrado " + id);
        }

        return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
    }
    
}
