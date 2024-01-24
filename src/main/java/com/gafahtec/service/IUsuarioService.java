package com.gafahtec.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.AuthenticationRequest;
import com.gafahtec.dto.request.AuthenticationResponse;
import com.gafahtec.dto.request.UsuarioRequest;
import com.gafahtec.dto.request.UsuarioResponse;
import com.gafahtec.model.auth.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IUsuarioService extends ICRUD<UsuarioRequest, UsuarioResponse ,Integer>{
	
	public Usuario register(UsuarioRequest request);
	
	public Usuario modificarUsuario(UsuarioRequest request);
	
	public AuthenticationResponse authenticate(AuthenticationRequest request);
	
	 public void refreshToken(
	          HttpServletRequest request,
	          HttpServletResponse response
	  ) throws IOException;

//	
//	Usuario registrarUsuarioEmpleado(UsuarioRequest usuarioRequest);
//
    Page<Usuario> listarPageable(Pageable pageable);
//
    Usuario getUsuarioPorId(Integer id);
//
//    Usuario modificarUsuarioEmpleado(UsuarioRequest usuarioRequest);

	 public  Optional<Usuario> findByEmail(String email);
}