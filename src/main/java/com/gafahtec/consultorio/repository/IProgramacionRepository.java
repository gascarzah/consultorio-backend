package com.gafahtec.consultorio.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.consultorio.Programacion;

@Repository
public interface IProgramacionRepository extends IGenericRepository<Programacion,Integer>{

    List<Programacion> findByActivo(Boolean activo);
    
    List<Programacion> findByRango(String rango);
    

    
    @Query("Select p from Programacion p  " )
//    @Query("Select p from Programacion p where p.idEmpresa = :idEmpresa " )
    Page<Programacion> listarProgramacionPageable(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);

    // Ordenar por fechaInicial (ascendente)
    @Query("SELECT p FROM Programacion p WHERE p.activo = :activo ORDER BY p.fechaInicial, p.fechaFinal ")
    List<Programacion> findByActivoOrderByFechaInicial(@Param("activo") Boolean activo);

//    // Ordenar por fechaFinal (ascendente)
//    @Query("SELECT p FROM Programacion p WHERE p.activo = :activo ORDER BY p.fechaFinal ")
//    Set<Programacion> findByActivoOrderByFechaFinal(@Param("activo") Boolean activo);
    
}
