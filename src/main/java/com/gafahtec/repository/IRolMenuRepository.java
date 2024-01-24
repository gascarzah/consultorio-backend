package com.gafahtec.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.gafahtec.model.auth.Rol;
import com.gafahtec.model.auth.RolMenu;
import com.gafahtec.model.auth.RolMenuPK;

public interface IRolMenuRepository extends IGenericRepository<RolMenu,RolMenuPK>{

	@Query("Select rol from Rol rol, RolMenu rm  "
			+ " left outer join rm.menu menu where rm.rol = rol and rol.idRol = :idRol  " )
	Page<RolMenu> listarPageable(Integer idRol, Pageable pageable);
	
	
	@Query("Select rm from RolMenu rm join rm.rol rol join rm.menu menu  where rol.idRol = :idRol order by menu.nombre  " )
	Set<RolMenu> findByRolOrder(Integer idRol);

}
