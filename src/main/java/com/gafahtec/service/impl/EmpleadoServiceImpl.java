package com.gafahtec.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.dto.request.EmpleadoRequest;
import com.gafahtec.dto.response.EmpleadoResponse;
import com.gafahtec.dto.response.EmpresaResponse;
import com.gafahtec.dto.response.PersonaResponse;
import com.gafahtec.model.auth.Empleado;
import com.gafahtec.model.auth.EmpleadoId;
import com.gafahtec.model.auth.Empresa;
import com.gafahtec.model.auth.Persona;
import com.gafahtec.repository.IEmpleadoRepository;
import com.gafahtec.repository.IPersonaRepository;
import com.gafahtec.service.IEmpleadoService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
@Transactional
public class EmpleadoServiceImpl  implements IEmpleadoService {

	
	private IEmpleadoRepository iEmpleadoRepository;
	private IPersonaRepository iPersonaRepository;

	@Override
	public Page<EmpleadoResponse> listarPageable(Pageable pageable) {
		
		return iEmpleadoRepository.findAll(pageable)
        		.map(this::entityToResponse);
	}

    @Override
    public Set<EmpleadoResponse> listarPorRol(Integer idRol) {
        
        return iEmpleadoRepository.findByRol(idRol)
        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<EmpleadoResponse> listarEmpleadosPorEmpresa(Integer idEmpresa) {
       
        return iEmpleadoRepository.findByEmpresa(Empresa.builder().idEmpresa(idEmpresa).build())
        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

	@Override
	public EmpleadoResponse registrar(EmpleadoRequest request) {
        var savedPersona = iPersonaRepository.save(Persona.builder()
                .numeroDocumento(request.getNumeroDocumento())
                .apellidoMaterno(request.getApellidoMaterno())
                .apellidoPaterno(request.getApellidoPaterno())
                .nombres(request.getNombres())
                .direccion(request.getDireccion())
                .build());
                
        var empleado = Empleado.builder()
                .empresa(Empresa.builder().idEmpresa(request.getIdEmpresa()).build())
                .persona(savedPersona)
                .build();
        System.out.println(empleado);
        var obj = iEmpleadoRepository.save(empleado);
		return entityToResponse(obj);
	}

	@Override
	public EmpleadoResponse modificar(EmpleadoRequest request) {
		 var empleado = new Empleado();
	        BeanUtils.copyProperties(request, empleado );
	        var obj = iEmpleadoRepository.save(empleado);
	        return entityToResponse(obj);
	}

	@Override
	public Set<EmpleadoResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmpleadoResponse listarPorId(EmpleadoId id) {
		// TODO Auto-generated method stub
//		entityToResponse(iEmpleadoRepository.findById(id).get())
		return null;
	}

	@Override
	public void eliminar(EmpleadoId id) {
		// TODO Auto-generated method stub
		
	}
	
	private EmpleadoResponse entityToResponse(Empleado entity) {
		var response = new EmpleadoResponse();
		BeanUtils.copyProperties(entity, response);
		
		var empresaResponse = new EmpresaResponse();
		BeanUtils.copyProperties(entity.getEmpresa(), empresaResponse);
		
		var personaResponse = new PersonaResponse();
		BeanUtils.copyProperties(entity.getPersona(), personaResponse);
		
		response.setIdEmpresa(entity.getIdEmpleado().getIdEmpresa());
		response.setNumeroDocumento(entity.getIdEmpleado().getNumeroDocumento());
		response.setEmpresa(empresaResponse);
		response.setPersona(personaResponse);
		
		return response;
	}
}
