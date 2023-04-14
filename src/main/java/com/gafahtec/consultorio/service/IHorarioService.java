package com.gafahtec.consultorio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.consultorio.model.Horario;


public interface IHorarioService extends ICRUD<Horario,Integer>{
    Page<Horario> listarPageable(Pageable pageable);
}
