package com.gafahtec.consultorio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gafahtec.consultorio.model.auth.Rol;

public interface IRolRepository extends IGenericRepository<Rol, Integer> {

	Rol findByNombre(String nombre);

	@Query("""
			SELECT r FROM Rol r
			WHERE r.nombre IS NOT NULL
			AND r.nombre != ''
			AND LOWER(r.nombre) LIKE LOWER(CONCAT('%', :search, '%'))
			""")
	Page<Rol> buscarRoles(@Param("search") String search, Pageable pageable);

}
