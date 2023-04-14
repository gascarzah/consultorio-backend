package com.gafahtec.consultorio.repository;

import com.gafahtec.consultorio.model.Usuario;

public interface IUsuarioRepository extends IGenericRepository<Usuario,Integer>{

	 Usuario findByEmail(String email);

}
