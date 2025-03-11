package com.gafahtec.consultorio.repository;

import com.gafahtec.consultorio.model.auth.Empresa;
import com.gafahtec.consultorio.model.auth.Rol;

public interface IEmpresaRepository extends IGenericRepository<Empresa,Integer>{
	
	Rol findByNombre(String nombre);

}
