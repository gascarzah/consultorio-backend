package com.gafahtec.consultorio.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.consultorio.Cita;
import com.gafahtec.consultorio.model.consultorio.ProgramacionDetalle;

@Repository
public interface ICitaRepository extends IGenericRepository<Cita, Integer> {

  @Query("Select c from Cita c join c.programacionDetalle pro where pro.idProgramacionDetalle = :idProgramacionDetalle order by c.idCita ")
  List<Cita> findByProgramacionDetalleOrderByCita(@Param("idProgramacionDetalle") Integer idProgramacionDetalle);

  @Query("""
            Select c from Cita c
            join ProgramacionDetalle pd on c.programacionDetalle.idProgramacionDetalle = pd.idProgramacionDetalle
      where pd.empleado.idEmpleado = :idEmpleado
        and pd.numeroDiaSemana = :numeroDiaSemana
             and pd.strFecha = :fecha
             order by c.idCita
             """)
  List<Cita> listaCitados(Integer idEmpleado, Integer numeroDiaSemana, String fecha);

  @Query("Select c from Cita c join c.historiaClinica cl where  cl.numeroDocumento = :numeroDocumento and c.atendido = true")
  Page<Cita> listaHistorialCitaCliente(String numeroDocumento, Pageable pageable);

  @Query("Select count(c) from Cita c join c.programacionDetalle pd where pd.idProgramacionDetalle = :idProgramacionDetalle")
  Integer getTotalCitas(@Param("idProgramacionDetalle") Integer idProgramacionDetalle);

  @Query("Select c from Cita c join c.programacionDetalle pd where  pd.idProgramacionDetalle = :idProgramacionDetalle and c.atendido = :atendido")
  List<Cita> getNoAtendidos(Integer idProgramacionDetalle, Boolean atendido);
  ////////////////////////////

  @Modifying
  @Query(value = "UPDATE Cita List id_cliente = null where id_cita = :idCita and id_horario = :idHorario and id_programacion_detalle = :idProgramacionDetalle ", nativeQuery = true)
  Integer eliminar(@Param("idCita") Integer idCita, @Param("idHorario") Integer idHorario,
      @Param("idProgramacionDetalle") Integer idProgramacionDetalle);

  @Modifying
  @Query(value = "UPDATE Cita List atendido = 1 where id_cita = :idCita ", nativeQuery = true)
  Integer updateAtencion(@Param("idCita") Integer idCita);

  @Query("Select c from Cita c ")
  // @Query("Select c from Cita c join c.cliente cl where cl.idCliente =
  // :idCliente" )
  List<Cita> listaHistorialCitaCliente(@Param("idCliente") Integer idCliente);

  List<Cita> findByAtendido(Boolean atendido);

  @Query("Select c from Cita c join c.programacionDetalle pd where  pd.fecha = :date ")
  List<Cita> listarPacientesHoy(LocalDate date);

  @Query("""
      SELECT c FROM Cita c
      JOIN c.historiaClinica h
      WHERE (

          LOWER(h.nombres) LIKE LOWER(CONCAT('%', :search, '%')) OR
          LOWER(h.apellidoPaterno) LIKE LOWER(CONCAT('%', :search, '%')) OR
          LOWER(h.apellidoMaterno) LIKE LOWER(CONCAT('%', :search, '%')) OR
          LOWER(CONCAT(h.nombres, ' ', h.apellidoPaterno, ' ', h.apellidoMaterno)) LIKE LOWER(CONCAT('%', :search, '%')) OR
          LOWER(h.telefono) LIKE LOWER(CONCAT('%', :search, '%')) OR
          LOWER(h.celular) LIKE LOWER(CONCAT('%', :search, '%')) OR
          LOWER(h.email) LIKE LOWER(CONCAT('%', :search, '%'))
      )
      """)
  Page<Cita> buscarCitas(@Param("search") String search, Pageable pageable);
}
