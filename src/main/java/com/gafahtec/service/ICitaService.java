package com.gafahtec.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.CitaRequest;
import com.gafahtec.dto.response.CitaResponse;
import com.gafahtec.model.consultorio.Cita;
import com.gafahtec.model.consultorio.ProgramacionDetalle;


public interface ICitaService extends ICRUD<CitaRequest, CitaResponse,Integer>{

	Page<CitaResponse> listarPageable(Pageable pageable);

    void registrarHorarios(List<ProgramacionDetalle> list);

    Set<CitaResponse> listarCitas(Integer idProgramacionDetalle);

//    Integer eliminar(Integer idCita, Integer idHorario, Integer idProgramacionDetalle);
    
    Integer updateAtencion( Integer idCita);

    Set<CitaResponse> listaCitados(Integer idEmpresa,String numeroDocumento, Integer numeroDiaSemana);
    
    Page<CitaResponse> listaHistorialCitaCliente(String numeroDocumento, Pageable paging);
    
    
//    Set<CitaResponse> listaHistorialCitaCliente(Integer idCliente);

    Set<Cita> listarNoAtendidos(Integer idProgramacionDetalle, Boolean noAtendidos);
    
     Cita modificarToEntity(Cita request);

   
}
