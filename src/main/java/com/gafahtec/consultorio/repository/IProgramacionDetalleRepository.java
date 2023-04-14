package com.gafahtec.consultorio.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.model.ProgramacionDetalle;

@Repository
public interface IProgramacionDetalleRepository extends IGenericRepository<ProgramacionDetalle, Integer> {

    @Query("SELECT pp FROM ProgramacionDetalle pp WHERE  pp.empleado.idEmpleado  = :idMedico and pp.estado = :estado and pp.numeroDiaSemana = :numeroDiaSemana")
    List<ProgramacionDetalle> citasPendientes(@Param("idMedico") Integer idMedico, @Param("estado") Boolean estado,
            @Param("numeroDiaSemana") Integer numeroDiaSemana);

    @Query("SELECT pp FROM ProgramacionDetalle pp WHERE  pp.empleado.idEmpleado = :idMedico and pp.estado = :estado")
    List<ProgramacionDetalle> findByEmpleadoAndPendiente(@Param("idMedico") Integer idMedico,
            @Param("estado") Integer estado);

//    List<ProgramacionDetalle> findByEmpleado(Empleado empleado);

    @Query("SELECT pp FROM ProgramacionDetalle pp WHERE pp.programacion.idProgramacion = :idProgramacion and pp.empleado.idEmpleado  = :idMedico")
    List<ProgramacionDetalle> getProgramacionMedico(@Param("idProgramacion") Integer idProgramacion,
            @Param("idMedico") Integer idMedico);

    List<ProgramacionDetalle> findByEstado(Boolean estado);

    @Query("SELECT pd FROM ProgramacionDetalle pd join pd.empleado emp join pd.programacion prog where  emp.idEmpleado = :idMedico and prog.strFechaInicial = :strFechaInicial and prog.strFechaFinal =:strFechaFinal")
    List<ProgramacionDetalle> verificaProgramacion(@Param("idMedico") Integer idMedico,
            @Param("strFechaInicial") String strFechaInicial, @Param("strFechaFinal") String strFechaFinal);

//    @Query("SELECT pp FROM ProgramacionDetalle pp WHERE pp.idProgramacionDetalle = :idProgramacionDetalle ")
//    ProgramacionDetalle listarPorIdDetalle(@Param("idProgramacionDetalle")Integer idProgramacionDetalle);

    List<ProgramacionDetalle> findByProgramacion(Programacion programacion);
    
    @Query("Select p from ProgramacionDetalle p  where  p.estado = false " )
    Page<ProgramacionDetalle> listarProgramacionEmpleadoPageable(Pageable pageable);

    
}
