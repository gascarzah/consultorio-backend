package com.gafahtec.consultorio.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.Programacion;

@Repository
public interface IProgramacionRepository extends IGenericRepository<Programacion,Integer>{

    List<Programacion> findByRango(String rango);
    
    List<Programacion> findByEstado(Boolean estado);
    @Query("Select p from Programacion p where p.idEmpresa = :idEmpresa " )
    Page<Programacion> listarProgramacionPageable(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);
    
    
    
}
