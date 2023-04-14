package com.gafahtec.consultorio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.Programacion;


public interface IProgramacionService extends ICRUD<Programacion,Integer>{

    

    List<Programacion> listarPorRango(String rango);

    List<Programacion> programacionEstado(Boolean b);

    Page<Programacion> listarPageable(Pageable pageable);

    Page<Programacion> listarProgramacionPageable(Integer idEmpresa,Pageable pageable);


}
