package com.gafahtec.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.ProgramacionRequest;
import com.gafahtec.dto.response.ProgramacionResponse;
import com.gafahtec.model.consultorio.Programacion;


public interface IProgramacionService extends ICRUD<ProgramacionRequest, ProgramacionResponse,Integer>{

    

    Set<ProgramacionResponse> listarPorRango(String rango);

    

    Page<ProgramacionResponse> listarPageable(Pageable pageable);

    Page<ProgramacionResponse> listarProgramacionPageable(Integer idEmpresa,Pageable pageable);

    Set<ProgramacionResponse> programacionActivo();

    public Set<Programacion> programacionEntityActivo();
    
    public Programacion modificarEntity(Programacion request);
}
