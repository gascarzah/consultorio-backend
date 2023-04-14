package com.gafahtec.consultorio.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.model.Persona;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IPersonaRepository;
import com.gafahtec.consultorio.service.IPersonaService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class PersonaServiceImpl extends CRUDImpl<Persona, String>  implements IPersonaService {

	
	private IPersonaRepository repo;
	
	@Override
	protected IGenericRepository<Persona, String> getRepo() {
		
		return repo;
	}
}
