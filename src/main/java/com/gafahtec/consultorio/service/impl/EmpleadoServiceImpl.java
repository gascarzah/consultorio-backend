package com.gafahtec.consultorio.service.impl;

import com.gafahtec.consultorio.model.Empleado;
import com.gafahtec.consultorio.repository.IEmpleadoRepository;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.service.IEmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@AllArgsConstructor
@Service
@Transactional
public class EmpleadoServiceImpl extends CRUDImpl<Empleado, Integer>  implements IEmpleadoService {

	
	private IEmpleadoRepository repo;
	
	@Override
	protected IGenericRepository<Empleado, Integer> getRepo() {
		
		return repo;
	}

	@Override
	public Page<Empleado> listarPageable(Pageable pageable) {
		
		return repo.findAll(pageable);
	}

    @Override
    public List<Empleado> listarPorRol(Integer idRol) {
        
        return repo.findByRol(idRol);
    }

    @Override
    public List<Empleado> listarPorTipoEmpleado(Integer idEmpresa, String descTipoEmpleado) {
       
        return repo.findByTipoEmpleadoEmpresa(idEmpresa, descTipoEmpleado);
    }
}
