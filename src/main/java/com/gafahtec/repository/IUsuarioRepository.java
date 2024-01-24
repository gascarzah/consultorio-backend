package com.gafahtec.repository;

import java.util.Optional;

import com.gafahtec.model.auth.Usuario;

public interface IUsuarioRepository extends IGenericRepository<Usuario,Integer>{

	Optional<Usuario> findByEmail(String email);

}
