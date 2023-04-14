package com.gafahtec.consultorio.service.impl;

import com.gafahtec.consultorio.model.Horario;
import com.gafahtec.consultorio.repository.IHorarioRepository;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.service.IHorarioService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class HorarioServiceImpl extends CRUDImpl<Horario, Integer>  implements IHorarioService {

	
	private IHorarioRepository repo;
	
	@Override
	protected IGenericRepository<Horario, Integer> getRepo() {
		
		return repo;
	}

    @Override
    public Page<Horario> listarPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }




}
