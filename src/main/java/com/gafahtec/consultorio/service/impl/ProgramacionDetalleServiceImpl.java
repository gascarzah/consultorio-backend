package com.gafahtec.consultorio.service.impl;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import com.gafahtec.consultorio.model.Feriado;
import com.gafahtec.consultorio.model.auth.Empresa;
import com.gafahtec.consultorio.model.consultorio.DiasPorEmpleado;
import com.gafahtec.consultorio.repository.*;
import com.gafahtec.consultorio.service.IFeriadoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.gafahtec.consultorio.dto.request.ProgramacionDetalleRequest;
import com.gafahtec.consultorio.dto.response.EmpleadoResponse;
import com.gafahtec.consultorio.dto.response.EmpresaResponse;
import com.gafahtec.consultorio.dto.response.PersonaResponse;
import com.gafahtec.consultorio.dto.response.ProgramacionDetalleHelperResponse;
import com.gafahtec.consultorio.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.consultorio.dto.response.ProgramacionResponse;
import com.gafahtec.consultorio.model.auth.Empleado;
import com.gafahtec.consultorio.model.consultorio.Programacion;
import com.gafahtec.consultorio.model.consultorio.ProgramacionDetalle;
import com.gafahtec.consultorio.service.IProgramacionDetalleService;
import com.gafahtec.consultorio.util.Constants;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Log4j2
@Transactional
public class ProgramacionDetalleServiceImpl implements IProgramacionDetalleService {

	private IProgramacionDetalleRepository iProgramacionDetalleRepository;
	private IProgramacionRepository iProgramacionRepository;
	private IEmpleadoRepository iEmpleadoRepository;
	private ICitaRepository iCitaRepository;
	private IFeriadoRepository iFeriadoRepository;
	private IDiasPorEmpleadoRepository iDiasPorEmpleadoRepository;


	@Override
	public List<ProgramacionDetalleResponse> registrar(ProgramacionDetalleRequest request) {
		var programacion = iProgramacionRepository.findById(request.getIdProgramacion()).orElseThrow(null);

		LocalDate startLocalDate = programacion.getFechaInicial().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		System.out.println("startLocalDate " + startLocalDate);
		LocalDate endLocalDate = programacion.getFechaFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println("endLocalDate " + endLocalDate);

		var programacionDetalleList = new HashSet<ProgramacionDetalle>();

		var empleado = iEmpleadoRepository.findById(request.getIdEmpleado())
				.orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

		for (String dia : request.getChecked()) {
			if (StringUtils.hasText(dia)) {
				LocalDate diaProgramado = startLocalDate.plusDays(Integer.parseInt(dia));
				DayOfWeek dayOfWeek = diaProgramado.getDayOfWeek();

				var programacionDetalle = ProgramacionDetalle.builder().programacion(programacion).fecha(diaProgramado)
						.diaSemana(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))
						.numeroDiaSemana(dayOfWeek.getValue()).empleado(empleado).activo(Constants.ACTIVO).build();
				var programacionDetalleSaved = iProgramacionDetalleRepository.save(programacionDetalle);

				log.info("Resultado", programacionDetalleSaved);
				programacionDetalleList.add(programacionDetalleSaved);
			}

		}

		return programacionDetalleList.stream().map(this::entityToResponse).collect(Collectors.toList());

	}

	@Override
	public Page<ProgramacionDetalleResponse> listarPageable(Pageable pageable) {
//		return iProgramacionDetalleRepository.listarPageable(pageable);
		return iProgramacionDetalleRepository.listarProgramacionDetalleActivoPageable(pageable).map(this::addTotalentityToResponse);
	}

	@Override
	public ProgramacionDetalleHelperResponse listarPorIdProgramacion(Integer idProgramacion) {
		var listaProgramacionDetalle = iProgramacionDetalleRepository
				.findByProgramacion(Programacion.builder().idProgramacion(idProgramacion).build());

		Set<Empleado> hsetEmpleado = new HashSet<>();
		List<Boolean> listDias = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			listDias.add(false);
		}

		for (ProgramacionDetalle pd : listaProgramacionDetalle) {
			hsetEmpleado.add(pd.getEmpleado());

			if (pd.getActivo()) {
				listDias.set((pd.getNumeroDiaSemana() - 1), true);
			}
		}

		System.out.println(listDias);

		if (hsetEmpleado.size() > 0) {
			List<Empleado> empleados = new ArrayList<>(hsetEmpleado);
			return ProgramacionDetalleHelperResponse.builder().empleado(empleados.get(0))
					.programacionDetalles(listaProgramacionDetalle).listaDias(listDias).build();

		}

		return null;
	}

	@Override
	public ProgramacionDetalleResponse modificar(@Valid ProgramacionDetalleRequest programacionDetalleRequest) {
		var lista = listarPorProgramacion(programacionDetalleRequest.getIdProgramacion());

		// nuevos
		var listaAgregar = new ArrayList<>(Arrays.asList(programacionDetalleRequest.getChecked()));
		List<ProgramacionDetalle> listProgramacionDetalleDelete = new ArrayList<>();
//	    for(int i= 0; i < lista.size(); i ++) {
		for (var pd : lista) {
//	        var pd = lista.get(i);
			listProgramacionDetalleDelete.add(pd);
			for (int x = 0; x < listaAgregar.size(); x++) {
				String numero = listaAgregar.get(x);
				if (StringUtils.hasText(numero)) {
					if (pd.getNumeroDiaSemana() - 1 == Integer.parseInt(numero)) {
						listProgramacionDetalleDelete.remove(pd);
						listaAgregar.remove(numero);
						break;
					}

				}

			}
		}

		System.out.println("listaTemporal " + listaAgregar);
		System.out.println("listProgramacionDetalleTemp " + listProgramacionDetalleDelete);
		if (!listaAgregar.isEmpty()) {
			programacionDetalleRequest
					.setChecked(Arrays.copyOf(listaAgregar.toArray(), listaAgregar.size(), String[].class));

			registrar(programacionDetalleRequest);
		}

		for (ProgramacionDetalle pgdel : listProgramacionDetalleDelete) {

			eliminar(pgdel.getIdProgramacionDetalle());
		}

		return null;
	}

	@Override
	public void eliminar(Integer id) {

		iProgramacionDetalleRepository.deleteById(id);
	}

	private List<ProgramacionDetalle> listarPorProgramacion(Integer idProgramacion) {

		return iProgramacionDetalleRepository
				.findByProgramacion(Programacion.builder().idProgramacion(idProgramacion).build());
	}

	public List<ProgramacionDetalleResponse> getProgramacionEmpleado(ProgramacionDetalleRequest request) {
		return iProgramacionDetalleRepository.getProgramacionEmpleado(request.getIdProgramacion(), request.getIdEmpresa(), request.getIdEmpleado()).stream()
				.map(this::entityToResponse).collect(Collectors.toList());

	}

	public Boolean existeProgramacionEmpleado(ProgramacionDetalleRequest request) {

		return getProgramacionEmpleado( request).isEmpty();
	}

	public ProgramacionDetalle modificarEntity(ProgramacionDetalle programacionDetalle) {
		return iProgramacionDetalleRepository.save(programacionDetalle);
	}
	//////////////////

	@Override
	public List<ProgramacionDetalleResponse> listarDiasProgramados(String numeroDocumento, Integer idEmpresa) throws ParseException {

		return iProgramacionDetalleRepository.findByEmpleadoAndEstado(numeroDocumento,idEmpresa, true).stream()
				.map(this::entityToResponse).collect(Collectors.toList());
	}

	@Override
	public List<ProgramacionDetalle> getProgramacionDetalleActivo(Boolean activo) {

		return iProgramacionDetalleRepository.findByActivo(activo);
	}



	@Override
	public List<ProgramacionDetalleResponse> verificaProgramacion(Integer idMedico, String fechaInicial,
			String fechaFinal) {
		return iProgramacionDetalleRepository.verificaProgramacion(idMedico, fechaInicial, fechaFinal).stream()
				.map(this::entityToResponse).collect(Collectors.toList());

	}

	@Override
	public List<ProgramacionDetalleResponse> citasPendientes(Integer idMedico, Integer numeroDiaSemana) {

		return iProgramacionDetalleRepository.citasPendientes(idMedico, Constants.ACTIVO, numeroDiaSemana).stream()
				.map(this::entityToResponse).collect(Collectors.toList());
	}


//	@Override
//	public Set<ProgramacionResponse> listar() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	@Override
//	public ProgramacionResponse listarPorId(Integer id) {
//		// TODO Auto-generated method stub
//		return entityToResponse(iProgramacionDetalleRepository.findById(id).get());
//	}
//
//

	private ProgramacionDetalleResponse entityToResponse(ProgramacionDetalle entity) {
//		System.out.println("entity");
		var response = new ProgramacionDetalleResponse();
		BeanUtils.copyProperties(entity, response);

		var programacion = new ProgramacionResponse();
		BeanUtils.copyProperties(entity.getProgramacion(), programacion);

		var empleado = new EmpleadoResponse();
		BeanUtils.copyProperties(entity.getEmpleado(), empleado);



		response.setEmpleado(empleado);
		response.setProgramacion(programacion);
		return response;
	}

	private ProgramacionDetalleResponse addTotalentityToResponse(ProgramacionDetalle entity) {
//		System.out.println("entity "+ entity);
		var response = entityToResponse(entity);
		var total = iCitaRepository.getTotalCitas(entity.getIdProgramacionDetalle());
//		System.out.println("total "+ total);
		response.setRegistrados(total);
		return response;
	}

	public Set<ProgramacionDetalleResponse> registrarAutomaticamente(ProgramacionDetalleRequest request) {
		//obtenerEmpleados
		Set<Empleado> listaEmpleado = iEmpleadoRepository.findByEmpresa(Empresa.builder().idEmpresa(request.getIdEmpresa()).build());
		//obtener solo 3 meses
//		List<Programacion> programacionesActiva = iProgramacionRepository.findByRango();

		//lista de feriados


		var programacion = iProgramacionRepository.findById(request.getIdProgramacion()).orElseThrow(null);

		LocalDate startLocalDate = programacion.getFechaInicial().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		System.out.println("startLocalDate " + startLocalDate);
		LocalDate endLocalDate = programacion.getFechaFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println("endLocalDate " + endLocalDate);

		var programacionDetalleList = new HashSet<ProgramacionDetalle>();

		var empleado = iEmpleadoRepository.findById(request.getIdEmpleado())
				.orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

		for (String dia : request.getChecked()) {
			if (StringUtils.hasText(dia)) {
				LocalDate diaProgramado = startLocalDate.plusDays(Integer.parseInt(dia));
				DayOfWeek dayOfWeek = diaProgramado.getDayOfWeek();

				var programacionDetalle = ProgramacionDetalle.builder().programacion(programacion).fecha(diaProgramado)
						.diaSemana(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))
						.numeroDiaSemana(dayOfWeek.getValue()).empleado(empleado).activo(Constants.ACTIVO).build();
				var programacionDetalleSaved = iProgramacionDetalleRepository.save(programacionDetalle);

				log.info("Resultado", programacionDetalleSaved);
				programacionDetalleList.add(programacionDetalleSaved);
			}

		}

		return programacionDetalleList.stream().map(this::entityToResponse).collect(Collectors.toSet());

	}

	public void generarProgramacionAutomaticaCada3Meses() {
		log.info("Iniciando verificación de programación para tercer mes...");
		List<Feriado> feriados = iFeriadoRepository.findAll();
		List<LocalDate> fechasFeriadas = feriados.stream()
				.map(Feriado::getFecha)
				.collect(Collectors.toList());
		LocalDate hoy = LocalDate.now();
		LocalDate fechaLimite = hoy.plusMonths(2);
		List<Programacion> programacionesActivas = iProgramacionRepository.findByActivo(true);
		var empleadosPorEmpresa =iDiasPorEmpleadoRepository.findByEmpresa(2);
	log.info("empleadosPorEmpresa ",empleadosPorEmpresa);
		for (Programacion prog : programacionesActivas) {
			LocalDate fechaInicio = prog.getFechaInicial()
					.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate();

			LocalDate fechaFin = prog.getFechaFinal()
					.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate();


			while (!fechaInicio.isAfter(fechaFin)) {

				if (fechaInicio.isAfter(fechaLimite)) {
					break;
				}


				DayOfWeek dia = fechaInicio.getDayOfWeek();
				int numeroDia = dia.getValue();
				boolean esFeriado = fechasFeriadas.contains(fechaInicio);


				if (dia != DayOfWeek.SUNDAY && !esFeriado) {
//					System.out.println("Programar día: " + fechaInicio + " - " + dia);
					for (DiasPorEmpleado dpe : empleadosPorEmpresa) {
						Set<Integer> diasHabilitados = dpe.getDias();
						if (diasHabilitados != null && diasHabilitados.contains(numeroDia)) {

							Integer idProgramacion = prog.getIdProgramacion();
							Integer idEmpleado = dpe.getEmpleado().getIdEmpleado();

							var programacionDetalle =iProgramacionDetalleRepository.existeProgramacionDetalle(idProgramacion,idEmpleado, numeroDia );
//							log.info("Buscando ProgramacionDetalle con: idProg={}, idEmp={}, dia={}",
//									idProgramacion, idEmpleado, numeroDia);



							log.info("programacionDetalle vacio? "+ObjectUtils.isEmpty(programacionDetalle));
							if(ObjectUtils.isEmpty(programacionDetalle)){
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

								String strFEcha = fechaInicio.format(formatter);
								DayOfWeek dayOfWeek = fechaInicio.getDayOfWeek();
								ProgramacionDetalle programacionDetalle1 =
										ProgramacionDetalle.builder()
												.programacion(prog)
												.empleado(Empleado.builder().idEmpleado(idEmpleado).build())
												.numeroDiaSemana(numeroDia)
												.fecha(fechaInicio)
												.strFecha(strFEcha)
												.activo(true)
												.diaSemana(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))
												.build();


								log.info("Programar día: " + programacionDetalle1);
								iProgramacionDetalleRepository.save(programacionDetalle1);

							}else{
								log.info("ya existe programacionDetalle "+programacionDetalle);
							}



						}
					}
				}

				// avanzar al siguiente día
				fechaInicio = fechaInicio.plusDays(1);
			}

		}

		log.info("Verificación completada.");
	}
}
