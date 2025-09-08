package com.gafahtec.consultorio.repository;

import com.gafahtec.consultorio.model.auth.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioRepository extends IGenericRepository<Usuario,Integer>{

	Optional<Usuario> findByEmail(String email);

	@Query("""
Select u from Usuario u where u.empleado.idEmpleado = :idEmpleado
""")
	Usuario findUsuarioByEmpleado(@Param("idEmpleado")Integer idEmpleado);
}
