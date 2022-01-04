package com.gafahtec.consultorio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gafahtec.consultorio.model.Medico;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IMedicoRepository;
import com.gafahtec.consultorio.service.IMedicoService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class MedicoServiceImpl extends CRUDImpl<Medico, Integer>  implements IMedicoService {

	
	private IMedicoRepository repo;
	
	@Override
	protected IGenericRepository<Medico, Integer> getRepo() {
		
		return repo;
	}

	@Override
	public Page<Medico> listarPageable(Pageable pageable) {
		
		return repo.findAll(pageable);
	}
}
