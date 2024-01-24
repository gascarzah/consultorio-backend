package com.gafahtec.repository;

import com.gafahtec.model.auth.Rol;

public interface IRolRepository extends IGenericRepository<Rol,Integer>{
	
	Rol findByNombre(String nombre);

}
