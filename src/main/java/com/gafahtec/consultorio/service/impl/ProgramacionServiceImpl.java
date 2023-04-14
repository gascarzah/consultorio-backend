package com.gafahtec.consultorio.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IProgramacionRepository;
import com.gafahtec.consultorio.service.IProgramacionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class ProgramacionServiceImpl extends CRUDImpl<Programacion, Integer>  implements IProgramacionService {

	
	private IProgramacionRepository repo;

//	private IProgramacionDetalleService iProgramacionDetalleService;

	@Override
	protected IGenericRepository<Programacion, Integer> getRepo() {
		
		return repo;
	}


	@Override
	public List<Programacion> listarPorRango(String rango) {
		return repo.findByRango(rango);
	}


    @Override
    public List<Programacion> programacionEstado(Boolean b) {
       
        return repo.findByEstado(b);
    }


    @Override
    public Page<Programacion> listarPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }


    @Override
    public Page<Programacion> listarProgramacionPageable(Integer idEmpresa, Pageable pageable) {
        // TODO Auto-generated method stub
        return repo.listarProgramacionPageable(idEmpresa,pageable);
    }




}
