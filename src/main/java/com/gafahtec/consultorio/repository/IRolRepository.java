package com.gafahtec.consultorio.repository;

import com.gafahtec.consultorio.model.auth.Rol;

public interface IRolRepository extends IGenericRepository<Rol,Integer>{
	
	Rol findByNombre(String nombre);

}
