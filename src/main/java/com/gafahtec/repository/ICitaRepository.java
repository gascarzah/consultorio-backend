package com.gafahtec.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.model.consultorio.Cita;
@Repository
public interface ICitaRepository extends IGenericRepository<Cita,Integer>{

    @Query("Select c from Cita c join c.programacionDetalle pro where pro.idProgramacionDetalle = :idProgramacionDetalle order by c.idCita ")
    Set<Cita> findByProgramacionDetalleOrderByCita(@Param("idProgramacionDetalle")Integer idProgramacionDetalle);
    
    @Query("Select c from Cita c join c.programacionDetalle pd "
    		+ "where pd.idEmpresa  = :idEmpresa"
    		+ " and pd.numeroDocumento = :numeroDocumento"
    		+ "  and pd.numeroDiaSemana = :numeroDiaSemana and pd.activo = true  order by c.idCita ")
    Set<Cita> listaCitados(Integer idEmpresa,String numeroDocumento,Integer numeroDiaSemana );
  
    @Query("Select c from Cita c join c.cliente cl where  cl.numeroDocumento = :numeroDocumento and c.atendido = true" )
    Page<Cita>  listaHistorialCitaCliente(String numeroDocumento, Pageable pageable);
    
    @Query("Select count(c) from Cita c join c.programacionDetalle pd where pd.idProgramacionDetalle = :idProgramacionDetalle  " )
    Integer getTotalCitas(Integer idProgramacionDetalle);
   
    
    @Query("Select c from Cita c join c.programacionDetalle pd where  pd.idProgramacionDetalle = :idProgramacionDetalle and c.atendido = :atendido" )
    Set<Cita> getNoAtendidos(Integer idProgramacionDetalle,Boolean atendido);
    
    ////////////////////////////
    
    @Modifying
    @Query(value = "UPDATE Cita set id_cliente = null where id_cita = :idCita and id_horario = :idHorario and id_programacion_detalle = :idProgramacionDetalle ", nativeQuery = true)
    Integer eliminar(@Param("idCita") Integer idCita, @Param("idHorario") Integer idHorario, @Param("idProgramacionDetalle") Integer idProgramacionDetalle);
   



    
    @Modifying
    @Query(value = "UPDATE Cita set atendido = 1 where id_cita = :idCita ", nativeQuery = true)
    Integer updateAtencion(@Param("idCita") Integer idCita);
    

    @Query("Select c from Cita c ")
//    @Query("Select c from Cita c join c.cliente cl where  cl.idCliente = :idCliente" )
    Set<Cita> listaHistorialCitaCliente(@Param("idCliente")Integer idCliente);


    
    Set<Cita> findByAtendido(Integer atendido);

    

}



