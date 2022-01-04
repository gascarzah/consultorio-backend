package com.gafahtec.consultorio.service.impl;

import org.springframework.stereotype.Service;

import com.gafahtec.consultorio.model.Tratamiento;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.ITratamientoRepository;
import com.gafahtec.consultorio.service.ITratamientoService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class TratamientoServiceImpl extends CRUDImpl<Tratamiento, Integer>  implements ITratamientoService {

	
	private ITratamientoRepository repo;
	
	@Override
	protected IGenericRepository<Tratamiento, Integer> getRepo() {
		
		return repo;
	}
}
