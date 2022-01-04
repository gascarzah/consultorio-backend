package com.gafahtec.consultorio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gafahtec.consultorio.model.Consulta;
import com.gafahtec.consultorio.repository.IConsultaRepository;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.service.IConsultaService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ConsultaServiceImpl extends CRUDImpl<Consulta, Integer>  implements IConsultaService {

	
	private IConsultaRepository repo;
	
	@Override
	protected IGenericRepository<Consulta, Integer> getRepo() {
		
		return repo;
	}

	@Override
	public Page<Consulta> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
