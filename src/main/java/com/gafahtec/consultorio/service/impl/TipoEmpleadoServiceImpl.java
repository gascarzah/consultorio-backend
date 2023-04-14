package com.gafahtec.consultorio.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.model.TipoEmpleado;
import com.gafahtec.consultorio.repository.ITipoEmpleadoRepository;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.service.ITipoEmpleadoService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class TipoEmpleadoServiceImpl extends CRUDImpl<TipoEmpleado, Integer>  implements ITipoEmpleadoService {

	
	private ITipoEmpleadoRepository repo;
	
	@Override
	protected IGenericRepository<TipoEmpleado, Integer> getRepo() {
		
		return repo;
	}

    @Override
    public Page<TipoEmpleado> listarPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
