package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.model.Usuario;


public interface UsuarioService extends ICRUD<Usuario, Integer>{
	

	Usuario getUsuario(String username);
	
	Usuario registrarUsuarioEmpleado(UsuarioRequest usuarioRequest);

    Page<Usuario> listarPageable(Pageable pageable);

    Usuario getUsuarioPorId(Integer id);

    Usuario modificarUsuarioEmpleado(UsuarioRequest usuarioRequest);


//	void addRoleToUser(String correo, String rol);
//	
//	public Rol registrarRol(Rol rol);
	
//	void grabarToken(AuthToken token);

}
