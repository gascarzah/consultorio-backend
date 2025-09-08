package com.gafahtec.consultorio.service.impl;

import com.gafahtec.consultorio.dto.request.EmpleadoRequest;
import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.dto.response.EmpleadoResponse;
import com.gafahtec.consultorio.model.auth.Empleado;
import com.gafahtec.consultorio.repository.IEmpleadoRepository;
import com.gafahtec.consultorio.repository.IEmpresaRepository;
import com.gafahtec.consultorio.service.IEmpleadoService;
import com.gafahtec.consultorio.service.IUsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class EmpleadoServiceImpl implements IEmpleadoService {

	private final IUsuarioService iUsuarioService;
	private IEmpleadoRepository iEmpleadoRepository;
	private IEmpresaRepository iEmpresaRepository;

	@Override
	public Page<EmpleadoResponse> listarPageable(Pageable pageable) {

		return iEmpleadoRepository.findAll(pageable)
				.map(this::entityToResponse);
	}

	@Override
	public List<EmpleadoResponse> listarPorRol(Integer idRol) {

		return iEmpleadoRepository.findByRol(idRol)
				.stream().map(this::entityToResponse).collect(Collectors.toList());
	}

	@Override
	public List<EmpleadoResponse> listarEmpleadosPorEmpresa(Integer idEmpresa) {
		var empresa = iEmpresaRepository
				.findById(idEmpresa)
				.orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada con ID: " + idEmpresa));
		return iEmpleadoRepository.findByEmpresa(empresa)
				.stream().map(this::entityToResponse).collect(Collectors.toList());
	}

	@Override
	public EmpleadoResponse registrar(EmpleadoRequest request) {

		var empresa = iEmpresaRepository
				.findById(request.getIdEmpresa())
				.orElseThrow(
						() -> new EntityNotFoundException("Empresa no encontrada con ID: " + request.getIdEmpresa()));

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

		var newUsuario = UsuarioRequest.builder()
				.email(request.getEmail())
				.idEmpleado(obj.getIdEmpleado())
				.idRol(request.getIdRol())
				.build();
		iUsuarioService.registrar(newUsuario);

		return entityToResponse(obj);
	}

	@Override
	public EmpleadoResponse modificar(EmpleadoRequest request) {
		// Buscar el empleado existente
		var empleadoExistente = iEmpleadoRepository.findById(request.getIdEmpleado())
				.orElseThrow(
						() -> new EntityNotFoundException("Empleado no encontrado con ID: " + request.getIdEmpleado()));

		// Buscar la empresa
		var empresa = iEmpresaRepository
				.findById(request.getIdEmpresa())
				.orElseThrow(
						() -> new EntityNotFoundException("Empresa no encontrada con ID: " + request.getIdEmpresa()));

		// Actualizar los campos del empleado
		empleadoExistente.setEmpresa(empresa);
		empleadoExistente.setNumeroDocumento(request.getNumeroDocumento());
		empleadoExistente.setApellidoMaterno(request.getApellidoMaterno());
		empleadoExistente.setApellidoPaterno(request.getApellidoPaterno());
		empleadoExistente.setNombres(request.getNombres());
		empleadoExistente.setDireccion(request.getDireccion());

		// Guardar los cambios del empleado
		var empleadoActualizado = iEmpleadoRepository.save(empleadoExistente);

		// Buscar el usuario existente asociado al empleado
		var usuarioExistente = iUsuarioService.findUsuarioByEmpleado(empleadoActualizado.getIdEmpleado());

		// Actualizar usuario asociado
		var usuarioRequest = UsuarioRequest.builder()
				.idUsuario(usuarioExistente.getIdUsuario()) // Importante: incluir el ID del usuario existente
				.email(request.getEmail())
				.idEmpleado(empleadoActualizado.getIdEmpleado())
				.idRol(request.getIdRol())
				.idEmpresa(request.getIdEmpresa())
				.nombres(request.getNombres())
				.apellidoPaterno(request.getApellidoPaterno())
				.apellidoMaterno(request.getApellidoMaterno())
				.numeroDocumento(request.getNumeroDocumento())
				.build();

		// Llamar a modificar en lugar de registrar
		iUsuarioService.modificar(usuarioRequest);

		return entityToResponse(empleadoActualizado);
	}

	@Override
	public List<EmpleadoResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmpleadoResponse listarPorId(Integer id) {

		Optional<Empleado> empleadoOpt = iEmpleadoRepository.findById(id);
		if (empleadoOpt.isPresent()) {
			return entityToResponse(empleadoOpt.get());
		} else {
			// Manejo personalizado (puedes lanzar excepción o retornar un valor por
			// defecto)
			throw new EntityNotFoundException("Empleado no encontrado con id: " + id);
		}
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub

	}

	private EmpleadoResponse entityToResponse(Empleado entity) {
		log.info("EmpleadoResponse " + entity);
		var response = new EmpleadoResponse();
		BeanUtils.copyProperties(entity, response);

		if (entity.getEmpresa() != null) {
			response.setIdEmpresa(entity.getEmpresa().getIdEmpresa());
		}
		response.setNumeroDocumento(entity.getNumeroDocumento());

		var usuario = iUsuarioService.findUsuarioByEmpleado(entity.getIdEmpleado());
		response.setIdRol(usuario.getIdRol());
		return response;
	}
}