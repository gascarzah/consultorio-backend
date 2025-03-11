package com.gafahtec.consultorio.repository;

import java.util.List;
import java.util.Set;

import com.gafahtec.consultorio.model.auth.Empresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gafahtec.consultorio.model.auth.Empleado;

public interface IEmpleadoRepository extends IGenericRepository<Empleado, Integer>{
	
	Set<Empleado> findByEmpresa(Empresa empresa);
	
//    @Query("SELECT emp FROM Usuario u join u.empleado emp join  u.roles r where r.idRol = :idRol   ")
	@Query("SELECT emp FROM Empleado  emp  ")
	List<Empleado> findByRol(@Param("idRol")Integer idRol);

//    @Query("SELECT emp FROM Empleado emp  ")
//    @Query("SELECT emp FROM Empleado   emp ")
//    List<Empleado> findByTipoEmpleadoEmpresa(@Param("idEmpresa")Integer idEmpresa, @Param("descTipoEmpleado")String descTipoEmpleado);
	


}
