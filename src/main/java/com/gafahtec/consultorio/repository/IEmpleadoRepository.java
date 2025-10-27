package com.gafahtec.consultorio.repository;

import java.util.List;
import java.util.Set;

import com.gafahtec.consultorio.model.auth.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gafahtec.consultorio.model.auth.Empleado;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends IGenericRepository<Empleado, Integer> {

	Set<Empleado> findByEmpresa(Empresa empresa);

	// @Query("SELECT emp FROM Usuario u join u.empleado emp join u.roles r where
	// r.idRol = :idRol ")
	@Query("SELECT emp FROM Empleado  emp  ")
	List<Empleado> findByRol(@Param("idRol") Integer idRol);

	@Query("""
			SELECT e FROM Empleado e
			WHERE (
				(e.nombres IS NOT NULL AND LOWER(e.nombres) LIKE LOWER(CONCAT('%', :search, '%'))) OR
				(e.apellidoPaterno IS NOT NULL AND LOWER(e.apellidoPaterno) LIKE LOWER(CONCAT('%', :search, '%'))) OR
				(e.apellidoMaterno IS NOT NULL AND LOWER(e.apellidoMaterno) LIKE LOWER(CONCAT('%', :search, '%'))) OR
				(e.numeroDocumento IS NOT NULL AND LOWER(e.numeroDocumento) LIKE LOWER(CONCAT('%', :search, '%')))
			)
			""")
	Page<Empleado> buscarEmpleados(@Param("search") String search, Pageable pageable);

	// @Query("SELECT emp FROM Empleado emp ")
	// @Query("SELECT emp FROM Empleado emp ")
	// List<Empleado> findByTipoEmpleadoEmpresa(@Param("idEmpresa")Integer
	// idEmpresa, @Param("descTipoEmpleado")String descTipoEmpleado);

}
