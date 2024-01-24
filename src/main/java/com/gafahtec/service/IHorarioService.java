package com.gafahtec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.HorarioRequest;
import com.gafahtec.dto.response.HorarioResponse;


public interface IHorarioService extends ICRUD<HorarioRequest,HorarioResponse,Integer>{
    Page<HorarioResponse> listarPageable(Pageable pageable);
}
