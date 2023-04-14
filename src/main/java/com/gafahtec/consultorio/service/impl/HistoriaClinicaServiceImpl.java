package com.gafahtec.consultorio.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.model.HistoriaClinica;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IHistoriaClinicaRepository;
import com.gafahtec.consultorio.service.IHistoriaClinicaService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class HistoriaClinicaServiceImpl extends CRUDImpl<HistoriaClinica, Integer>  implements IHistoriaClinicaService {

	
	private IHistoriaClinicaRepository repo;
	
	@Override
	protected IGenericRepository<HistoriaClinica, Integer> getRepo() {
		
		return repo;
	}
}
