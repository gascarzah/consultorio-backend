package com.gafahtec.consultorio.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.Cita;
@Repository
public interface ICitaRepository extends IGenericRepository<Cita,Integer>{

    @Query("Select c from Cita c join c.programacionDetalle pro where pro.idProgramacionDetalle = :idProgramacionDetalle order by c.idCita ")
    List<Cita> findByProgramacionDetalleOrderByCita(@Param("idProgramacionDetalle")Integer idProgramacionDetalle);
    
    @Modifying
    @Query(value = "UPDATE Cita set id_cliente = null where id_cita = :idCita and id_horario = :idHorario and id_programacion_detalle = :idProgramacionDetalle ", nativeQuery = true)
    Integer eliminar(@Param("idCita") Integer idCita, @Param("idHorario") Integer idHorario, @Param("idProgramacionDetalle") Integer idProgramacionDetalle);
    
    @Modifying
    @Query(value = "UPDATE Cita set atendido = 1 where id_cita = :idCita ", nativeQuery = true)
    Integer updateAtencion(@Param("idCita") Integer idCita);
    
    @Query("Select c from Cita c join c.programacionDetalle pro where pro.empleado.idEmpleado  = :idMedico and pro.numeroDiaSemana = :numeroDiaSemana and pro.estado = 0  order by c.idCita ")
    List<Cita> listaCitados(@Param("idMedico")Integer idMedico,@Param("numeroDiaSemana")Integer numeroDiaSemana );

    @Query("Select c from Cita c join c.cliente cl where  cl.idCliente = :idCliente" )
    List<Cita> listaHistorialCitaCliente(@Param("idCliente")Integer idCliente);

    
    @Query("Select c from Cita c join c.cliente cl where  cl.idCliente = :idCliente and c.atendido != 0" )
    Page<Cita>  findByClienteNoAtendidos(@Param("idCliente")Integer idCliente, Pageable pageable);
    
    List<Cita> findByAtendido(Integer atendido);

    
    @Query("Select c from Cita c join c.programacionDetalle pd where  pd.idProgramacionDetalle = :idProgramacionDetalle and c.atendido = :atendido" )
    List<Cita> getNoAtendidos(@Param("idProgramacionDetalle")Integer idProgramacionDetalle,@Param("atendido") Integer atendido);
}



