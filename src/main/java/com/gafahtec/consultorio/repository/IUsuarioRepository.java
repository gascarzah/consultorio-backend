package com.gafahtec.consultorio.repository;

import com.gafahtec.consultorio.model.auth.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioRepository extends IGenericRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

	@Query("""
			Select u from Usuario u where u.empleado.idEmpleado = :idEmpleado
			""")
	Usuario findUsuarioByEmpleado(@Param("idEmpleado") Integer idEmpleado);

	@Query("""
			SELECT u FROM Usuario u
			JOIN u.empleado e
			WHERE u.email IS NOT NULL
			AND u.email != ''
			AND (
				(u.email IS NOT NULL AND LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) OR
				(e.nombres IS NOT NULL AND LOWER(e.nombres) LIKE LOWER(CONCAT('%', :search, '%'))) OR
				(e.apellidoPaterno IS NOT NULL AND LOWER(e.apellidoPaterno) LIKE LOWER(CONCAT('%', :search, '%'))) OR
				(e.apellidoMaterno IS NOT NULL AND LOWER(e.apellidoMaterno) LIKE LOWER(CONCAT('%', :search, '%')))
			)
			""")
	Page<Usuario> buscarUsuarios(@Param("search") String search, Pageable pageable);
}
