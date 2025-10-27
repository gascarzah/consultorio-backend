package com.gafahtec.consultorio.service.impl;

import com.gafahtec.consultorio.dto.request.EmpleadoRequest;
import com.gafahtec.consultorio.dto.request.UsuarioRequest;
import com.gafahtec.consultorio.dto.response.EmpleadoResponse;
import com.gafahtec.consultorio.dto.response.TipoEmpleadoResponse;
import com.gafahtec.consultorio.model.auth.Empleado;
import com.gafahtec.consultorio.model.auth.TipoEmpleado;
import com.gafahtec.consultorio.repository.IEmpleadoRepository;
import com.gafahtec.consultorio.repository.IEmpresaRepository;
import com.gafahtec.consultorio.repository.ITipoEmpleadoRepository;
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
	private ITipoEmpleadoRepository iTipoEmpleadoRepository;

	@Override
	public Page<EmpleadoResponse> listarPageable(Pageable pageable) {

		return iEmpleadoRepository.findAll(pageable)
				.map(this::entityToResponse);
	}

	@Override
	public Page<EmpleadoResponse> buscarEmpleados(String search, Pageable pageable) {
		System.out.println("=== BUSCAR EMPLEADOS DEBUG ===");
		System.out.println("Search term: '" + search + "'");

		Page<Empleado> empleados = iEmpleadoRepository.buscarEmpleados(search, pageable);
		System.out.println("Total empleados found: " + empleados.getTotalElements());

		return empleados.map(this::entityToResponse);
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

		TipoEmpleado tipoEmpleado = iTipoEmpleadoRepository.getReferenceById(request.getIdTipoEmpleado());

		var empleado = Empleado.builder()
				.empresa(empresa)
				.numeroDocumento(request.getNumeroDocumento())
				.apellidoMaterno(request.getApellidoMaterno())
				.apellidoPaterno(request.getApellidoPaterno())
				.nombres(request.getNombres())
				.direccion(request.getDireccion())
				.tipoEmpleado(tipoEmpleado)
				.build();
		System.out.println(empleado);
		var obj = iEmpleadoRepository.save(empleado);

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
		TipoEmpleado tipoEmpleado = iTipoEmpleadoRepository.getReferenceById(request.getIdTipoEmpleado());
		// Actualizar los campos del empleado
		empleadoExistente.setEmpresa(empresa);
		empleadoExistente.setNumeroDocumento(request.getNumeroDocumento());
		empleadoExistente.setApellidoMaterno(request.getApellidoMaterno());
		empleadoExistente.setApellidoPaterno(request.getApellidoPaterno());
		empleadoExistente.setNombres(request.getNombres());
		empleadoExistente.setDireccion(request.getDireccion());
		empleadoExistente.setTipoEmpleado(tipoEmpleado);
		// Guardar los cambios del empleado
		var empleadoActualizado = iEmpleadoRepository.save(empleadoExistente);

		// // Buscar el usuario existente asociado al empleado
		// var usuarioExistente =
		// iUsuarioService.findUsuarioByEmpleado(empleadoActualizado.getIdEmpleado());
		//
		// // Actualizar usuario asociado
		// var usuarioRequest = UsuarioRequest.builder()
		// .idUsuario(usuarioExistente.getIdUsuario()) // Importante: incluir el ID del
		// usuario existente
		// .email(request.getEmail())
		// .idEmpleado(empleadoActualizado.getIdEmpleado())
		// .idEmpresa(request.getIdEmpresa())
		// .nombres(request.getNombres())
		// .apellidoPaterno(request.getApellidoPaterno())
		// .apellidoMaterno(request.getApellidoMaterno())
		// .numeroDocumento(request.getNumeroDocumento())
		// .build();
		//
		// // Llamar a modificar en lugar de registrar
		// iUsuarioService.modificar(usuarioRequest);

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

		if (entity.getTipoEmpleado() != null) {
			response.setIdTipoEmpleado(entity.getTipoEmpleado().getIdTipoEmpleado());
			response.setTipoEmpleadoNombre(entity.getTipoEmpleado().getNombre());
		}
		// // Solo intentar obtener el usuario si existe
		// try {
		// var usuario = iUsuarioService.findUsuarioByEmpleado(entity.getIdEmpleado());
		// if (usuario != null) {
		// response.setIdRol(usuario.getIdRol());
		// }
		// } catch (Exception e) {
		// // Si no hay usuario asociado, continuar sin error
		// log.info("No hay usuario asociado al empleado con ID: " +
		// entity.getIdEmpleado());
		// }
		return response;
	}
}