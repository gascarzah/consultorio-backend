package com.gafahtec.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gafahtec.model.auth.Empleado;
import com.gafahtec.model.auth.EmpleadoId;
import com.gafahtec.model.auth.Empresa;

public interface IEmpleadoRepository extends IGenericRepository<Empleado, EmpleadoId>{
	
	Set<Empleado> findByEmpresa(Empresa empresa);
	
//    @Query("SELECT emp FROM Usuario u join u.empleado emp join  u.roles r where r.idRol = :idRol   ")
	@Query("SELECT emp FROM Empleado  emp  ")
	List<Empleado> findByRol(@Param("idRol")Integer idRol);

//    @Query("SELECT emp FROM Empleado emp  ")
//    @Query("SELECT emp FROM Empleado   emp ")
//    List<Empleado> findByTipoEmpleadoEmpresa(@Param("idEmpresa")Integer idEmpresa, @Param("descTipoEmpleado")String descTipoEmpleado);
	

    
//    @Query("SELECT emp FROM Empleado emp order by emp.persona.apellidoPaterno desc   ")
    @Query("SELECT emp FROM Empleado   emp ")
    List<Empleado> findByTodos();
}
