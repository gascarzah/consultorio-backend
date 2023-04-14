package com.gafahtec.consultorio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.model.Empresa;
import com.gafahtec.consultorio.repository.IEmpresaRepository;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.service.IEmpresaService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class EmpresaServiceImpl extends CRUDImpl<Empresa, Integer>  implements IEmpresaService {

	
	private IEmpresaRepository repo;
	
	@Override
	protected IGenericRepository<Empresa, Integer> getRepo() {
		
		return repo;
	}

    @Override
    public Page<Empresa> listarPageable(Pageable pageable) {
        // TODO Auto-generated method stub
        return repo.findAll(pageable);
    }
}
