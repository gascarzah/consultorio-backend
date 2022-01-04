package com.gafahtec.consultorio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gafahtec.consultorio.model.Paciente;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IPacienteRepository;
import com.gafahtec.consultorio.service.IPacienteService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente, Integer>  implements IPacienteService {

	
	private IPacienteRepository repo;
	
	@Override
	protected IGenericRepository<Paciente, Integer> getRepo() {
		
		return repo;
	}
	
	@Override
	public Page<Paciente> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
