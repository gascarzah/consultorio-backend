package com.gafahtec.consultorio.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.dto.request.ProgramacionDetalleRequest;
import com.gafahtec.consultorio.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.model.ProgramacionDetalle;

public interface IProgramacionDetalleService extends ICRUD<ProgramacionDetalle, Integer> {
//    public List<ProgramacionDetalle> generarProgramacionDetalle(Programacion programacion,
//            ProgramacionRequest programacionRequest);

//    List<ProgramacionDetalle> generarProgramacion(Programacion programacion, ProgramacionRequest programacionRequest)
//            throws ParseException;

    List<ProgramacionDetalle> generarProgramacionDetalle(Programacion programacion, ProgramacionDetalleRequest programacionRequest)
            throws ParseException;
    
    List<ProgramacionDetalle> generarDiasProgramados(Integer idMedico) throws ParseException;

//    public List<ProgramacionDetalle> getProgramacionConDetalle(Programacion programacion);

    public List<ProgramacionDetalle> getProgramacionMedico(Integer idProgramacion, Integer idMedico);
    
    
    public List<ProgramacionDetalle> programacionDias(Boolean estado);

    public List<ProgramacionDetalle> verificaProgramacion(Integer idMedico, String fechaInicial, String fechaFinal);

    public List<ProgramacionDetalle> citasPendientes(Integer idMedico, Integer numeroDiaSemana);

    public Page<ProgramacionDetalle> listarPageable(Pageable pageable);

    ProgramacionDetalleResponse listarPorIdProgramacion(Integer idProgramacion);



    List<ProgramacionDetalle> listarPorProgramacion(Integer idProgramacion);

    Page<ProgramacionDetalle> listarProgramacionEmpleadoPageable(Pageable paging);

  
}
