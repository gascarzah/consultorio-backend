package com.gafahtec.consultorio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.model.Rol;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IRolRepository;
import com.gafahtec.consultorio.service.IRolService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class RolServiceImpl extends CRUDImpl<Rol, Integer>  implements IRolService {

	
	private IRolRepository repo;
	
	@Override
	protected IGenericRepository<Rol, Integer> getRepo() {
		
		return repo;
	}

    @Override
    public Page<Rol> listarPageable(Pageable pageable) {
        // TODO Auto-generated method stub
        return repo.findAll(pageable);
    }
}
