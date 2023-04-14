package com.gafahtec.consultorio.repository;

import com.gafahtec.consultorio.model.Empresa;
import com.gafahtec.consultorio.model.Rol;

public interface IEmpresaRepository extends IGenericRepository<Empresa,Integer>{
	
	Rol findByNombre(String nombre);

}
