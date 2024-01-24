package com.gafahtec.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.model.consultorio.Programacion;
import com.gafahtec.model.consultorio.ProgramacionDetalle;

@Repository
public interface IProgramacionDetalleRepository extends IGenericRepository<ProgramacionDetalle, Integer> {

    Set<ProgramacionDetalle> findByProgramacion(Programacion programacion);
    
  @Query("SELECT pd FROM ProgramacionDetalle pd WHERE pd.programacion.idProgramacion = :idProgramacion and pd.idEmpresa  = :idEmpresa and  pd.numeroDocumento  = :numeroDocumento")
  Set<ProgramacionDetalle> getProgramacionEmpleado(Integer idProgramacion,
          Integer idEmpresa, String numeroDocumento);

  @Query("SELECT pd FROM ProgramacionDetalle pd WHERE  pd.numeroDocumento = :numeroDocumento and pd.idEmpresa = :idEmpresa and pd.activo = :activo")
  Set<ProgramacionDetalle> findByEmpleadoAndEstado(String numeroDocumento, Integer idEmpresa, Boolean activo);

//  @Query("SELECT new com.gafahtec.dto.response.ProgramacionDetalleResponse"
//  		+ "(pd.idProgramacionDetalle, pd.fecha, pd.diaSemana, pd.numeroDiaSemana, pd.estado, pd.empleado, pd.programacion, count(c.idCita ) ) "
//  		+ "FROM Cita c join cita.programacionDetalle pd group by pd.idProgramacionDetalle, pd.fecha, pd.diaSemana, pd.numeroDiaSemana, pd.empleado, pd.programacion ")
//	Page<ProgramacionDetalleResponse> listarPageable(Pageable pageable);
  
  @Query("Select p from ProgramacionDetalle p   " )
  Page<ProgramacionDetalle> listarProgramacionDetalleActivoPageable(Pageable pageable);
    //////////////////////////
	
	@Query("SELECT pp FROM ProgramacionDetalle pp")
//    @Query("SELECT pp FROM ProgramacionDetalle pp WHERE  pp.empleado.idEmpleado  = :idMedico and pp.estado = :estado and pp.numeroDiaSemana = :numeroDiaSemana")
    Set<ProgramacionDetalle> citasPendientes(@Param("idMedico") Integer idMedico, @Param("estado") Boolean estado,
            @Param("numeroDiaSemana") Integer numeroDiaSemana);



    


    Set<ProgramacionDetalle> findByActivo(Boolean activo);

    @Query("SELECT pp FROM ProgramacionDetalle pp")
//    @Query("SELECT pd FROM ProgramacionDetalle pd join pd.empleado emp join pd.programacion prog where  emp.idEmpleado = :idMedico and prog.strFechaInicial = :strFechaInicial and prog.strFechaFinal =:strFechaFinal")
    Set<ProgramacionDetalle> verificaProgramacion(@Param("idMedico") Integer idMedico,
            @Param("strFechaInicial") String strFechaInicial, @Param("strFechaFinal") String strFechaFinal);


    




    
}
