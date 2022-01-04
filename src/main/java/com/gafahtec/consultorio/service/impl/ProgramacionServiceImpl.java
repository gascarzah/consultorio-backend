package com.gafahtec.consultorio.service.impl;

import org.springframework.stereotype.Service;

import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IProgramacionRepository;
import com.gafahtec.consultorio.service.IProgramacionService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ProgramacionServiceImpl extends CRUDImpl<Programacion, Integer>  implements IProgramacionService {

	
	private IProgramacionRepository repo;
	
	@Override
	protected IGenericRepository<Programacion, Integer> getRepo() {
		
		return repo;
	}
}
