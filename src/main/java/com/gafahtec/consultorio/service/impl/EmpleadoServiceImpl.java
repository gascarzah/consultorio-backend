package com.gafahtec.consultorio.service.impl;

import com.gafahtec.consultorio.dto.request.EmpleadoRequest;
import com.gafahtec.consultorio.dto.response.EmpleadoResponse;
import com.gafahtec.consultorio.dto.response.EmpresaResponse;
import com.gafahtec.consultorio.dto.response.PersonaResponse;
import com.gafahtec.consultorio.model.auth.Empleado;
import com.gafahtec.consultorio.repository.IEmpleadoRepository;
import com.gafahtec.consultorio.repository.IEmpresaRepository;
import com.gafahtec.consultorio.service.IEmpleadoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class EmpleadoServiceImpl  implements IEmpleadoService {

	
	private IEmpleadoRepository iEmpleadoRepository;
	private IEmpresaRepository iEmpresaRepository;

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
		var empresa = iEmpresaRepository
				.findById(idEmpresa)
				.orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con ID: " + idEmpresa));
        return iEmpleadoRepository.findByEmpresa(empresa)
        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

	@Override
	public EmpleadoResponse registrar(EmpleadoRequest request) {
		var empresa = iEmpresaRepository
				.findById(request.getIdEmpresa())
				.orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con ID: " + request.getIdEmpresa()));
                
        var empleado = Empleado.builder()
                .empresa(empresa)
				.numeroDocumento(request.getNumeroDocumento())
				.apellidoMaterno(request.getApellidoMaterno())
				.apellidoPaterno(request.getApellidoPaterno())
				.nombres(request.getNombres())
				.direccion(request.getDireccion())
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
	public EmpleadoResponse listarPorId(Integer id) {
		// TODO Auto-generated method stub
//		entityToResponse(iEmpleadoRepository.findById(id).get())
		return null;
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	private EmpleadoResponse entityToResponse(Empleado entity) {
		log.info("EmpleadoResponse "+ entity);
		var response = new EmpleadoResponse();
		BeanUtils.copyProperties(entity, response);

		response.setIdEmpresa(entity.getEmpresa().getIdEmpresa());
		response.setNumeroDocumento(entity.getNumeroDocumento());

		return response;
	}
}
