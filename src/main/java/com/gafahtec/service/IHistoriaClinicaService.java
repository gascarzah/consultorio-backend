package com.gafahtec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.HistoriaClinicaRequest;
import com.gafahtec.dto.response.HistoriaClinicaResponse;


public interface IHistoriaClinicaService extends ICRUD<HistoriaClinicaRequest, HistoriaClinicaResponse,Integer>{

	Page<HistoriaClinicaResponse> listarPageable(Pageable pageable);
}
