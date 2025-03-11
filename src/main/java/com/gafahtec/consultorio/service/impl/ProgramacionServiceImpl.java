package com.gafahtec.consultorio.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.ProgramacionRequest;
import com.gafahtec.consultorio.dto.response.EmpleadoResponse;
import com.gafahtec.consultorio.dto.response.ProgramacionResponse;
import com.gafahtec.consultorio.model.consultorio.Programacion;
import com.gafahtec.consultorio.repository.IProgramacionRepository;
import com.gafahtec.consultorio.service.IProgramacionService;
import com.gafahtec.consultorio.util.Constants;
import com.gafahtec.consultorio.util.Utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class ProgramacionServiceImpl  implements IProgramacionService {

	
	private IProgramacionRepository iProgramacionRepository;




	@Override
	public Set<ProgramacionResponse> listarPorRango(String rango) {
		return iProgramacionRepository.findByRango(rango).stream().map(this::entityToResponse).collect(Collectors.toSet());
	}





    @Override
    public Page<ProgramacionResponse> listarPageable(Pageable pageable) {
        return iProgramacionRepository.findAll(pageable).map(this::entityToResponse);
    }


    @Override
    public Page<ProgramacionResponse> listarProgramacionPageable(Integer idEmpresa, Pageable pageable) {
        // TODO Auto-generated method stub
        return iProgramacionRepository.listarProgramacionPageable(idEmpresa,pageable).map(this::entityToResponse);
    }


	@Override
	public ProgramacionResponse registrar(ProgramacionRequest request) {

        String strFechaInicial = Utils.getFecha2String(request.getFechaInicial());
        String strFechaFinal = Utils.getFecha2String(request.getFechaFinal());

        String rango = strFechaInicial + " - " + strFechaFinal;

        var programacion = new Programacion();
        BeanUtils.copyProperties(  request, programacion );
        programacion.setRango(rango);
        programacion.setActivo(Constants.ACTIVO);
        programacion.setStrFechaFinal(strFechaFinal);
        programacion.setStrFechaInicial(strFechaInicial);
        
        var obj = iProgramacionRepository.save(programacion);
		return entityToResponse(obj);
	}


	@Override
	public ProgramacionResponse modificar(ProgramacionRequest request) {
		var programacion = new Programacion();
        BeanUtils.copyProperties(  request, programacion );
		var obj =modificarEntity(programacion);
		return entityToResponse(obj);
	}
	

	
	public Programacion modificarEntity(Programacion request) {
		return iProgramacionRepository.save(request);
	}


	@Override
	public Set<ProgramacionResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ProgramacionResponse listarPorId(Integer id) {
		return entityToResponse(iProgramacionRepository.findById(id).get());
	}


	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}

	private ProgramacionResponse entityToResponse(Programacion entity) {
		var response = new ProgramacionResponse();
		BeanUtils.copyProperties(entity, response);
		
		
		
		return response;
	}


	@Override
	public Set<ProgramacionResponse> programacionActivo() {
		 return programacionEntityActivo().stream().map(this::entityToResponse).collect(Collectors.toSet());
	}

	public Set<Programacion> programacionEntityActivo() {
		 return iProgramacionRepository.findByActivo(Constants.ACTIVO);
	}
}
