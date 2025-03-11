package com.gafahtec.consultorio.repository;

import java.util.Optional;

import com.gafahtec.consultorio.model.auth.Usuario;

public interface IUsuarioRepository extends IGenericRepository<Usuario,Integer>{

	Optional<Usuario> findByEmail(String email);

}
