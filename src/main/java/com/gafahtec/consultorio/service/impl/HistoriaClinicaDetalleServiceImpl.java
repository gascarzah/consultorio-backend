package com.gafahtec.consultorio.service.impl;

import org.springframework.stereotype.Service;

import com.gafahtec.consultorio.model.HistoriaClinicaDetalle;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IHistoriaClinicaDetalleRepository;
import com.gafahtec.consultorio.service.IHistoriaClinicaDetalleService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class HistoriaClinicaDetalleServiceImpl extends CRUDImpl<HistoriaClinicaDetalle, Integer>  implements IHistoriaClinicaDetalleService {

	
	private IHistoriaClinicaDetalleRepository repo;
	
	@Override
	protected IGenericRepository<HistoriaClinicaDetalle, Integer> getRepo() {
		
		return repo;
	}
}
