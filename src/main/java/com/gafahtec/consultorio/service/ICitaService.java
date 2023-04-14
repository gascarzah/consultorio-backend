package com.gafahtec.consultorio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.Cita;
import com.gafahtec.consultorio.model.ProgramacionDetalle;


public interface ICitaService extends ICRUD<Cita,Integer>{

	Page<Cita> listarPageable(Pageable pageable);

    void registrarHorarios(List<ProgramacionDetalle> list);

    List<Cita> listarCitas(Integer idProgramacionDetalle);

    Integer eliminar(Integer idCita, Integer idHorario, Integer idProgramacionDetalle);
    
    Integer updateAtencion( Integer idCita);

    List<Cita> listaCitados(Integer idMedico, Integer numeroDiaSemana);

    List<Cita> listaHistorialCitaCliente(Integer idCliente);

    Page<Cita> listaHistorialCitaCliente(Integer idCliente, Pageable paging);



    List<Cita> listarNoAtendidos(Integer idProgramacionDetalle, Integer noAtendidos);

   
}
