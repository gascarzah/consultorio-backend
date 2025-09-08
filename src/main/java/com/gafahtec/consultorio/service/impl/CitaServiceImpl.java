package com.gafahtec.consultorio.service.impl;

import com.gafahtec.consultorio.dto.request.CitaRequest;
import com.gafahtec.consultorio.dto.response.*;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.consultorio.Cita;
import com.gafahtec.consultorio.model.consultorio.HistoriaClinica;
import com.gafahtec.consultorio.model.consultorio.Horario;
import com.gafahtec.consultorio.model.consultorio.ProgramacionDetalle;
import com.gafahtec.consultorio.repository.ICitaRepository;
import com.gafahtec.consultorio.repository.IHorarioRepository;
import com.gafahtec.consultorio.service.ICitaService;
import com.gafahtec.consultorio.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class CitaServiceImpl  implements ICitaService {

    private ICitaRepository iCitaRepository;

    private IHorarioRepository iHorarioRepository;



    @Override
    public Page<CitaResponse> listarPageable(Pageable pageable) throws ResourceNotFoundException{
        return iCitaRepository.findAll(pageable)
        		.map(this::entityToResponse);
    }

    public void registrarHorarios(List<ProgramacionDetalle> list) {
      
            List<Horario> horarios = iHorarioRepository.findAll();

            for (ProgramacionDetalle programacionDetalle : list) {

                for (Horario horario : horarios) {

                	iCitaRepository.save(Cita.builder()
                            .programacionDetalle(programacionDetalle)
                            .horario(horario)
                            .atendido(Constants.ACTIVO)
                            .build());
                }
            }
    }

    @Override
    public List<CitaResponse> listarCitas(Integer idProgramacionDetalle) {
        
        return iCitaRepository.findByProgramacionDetalleOrderByCita(idProgramacionDetalle)
        		.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    
    @Override
    public Integer eliminar(Integer idCita, Integer idHorario, Integer idProgramacionDetalle) {
        
        return iCitaRepository.eliminar(idCita, idHorario, idProgramacionDetalle);
    }

    
    @Override
    public Integer updateAtencion(Integer idCita) {
        
        return iCitaRepository.updateAtencion(idCita);
    }

    @Override
    public List<CitaResponse> listaCitados(Integer idEmpleado, Integer numeroDiaSemana) {
		LocalDate fechaActual = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaFormateada = fechaActual.format(formato);

//		var atencion = iCitaRepository.listaCitados( idEmpleado, numeroDiaSemana, fechaFormateada)
//				.stream().map(c -> {
//
//					var list = new ArrayList<CitadosResponse>();
//
//
//
//					var citado = CitadosResponse.builder().paciente(c.getHistoriaClinica().getApellidoPaterno() + " " +
//							c.getProgramacionDetalle().getEmpleado().getApellidoMaterno() + " " +
//							c.getProgramacionDetalle().getEmpleado().getNombres())
//							.horario(c.getHorario().getDescripcion())
//							.build();
//					list.add(citado);
//
//					var doctor =DoctorDisponibleResponse.builder().medico(c.getProgramacionDetalle().getEmpleado().getApellidoPaterno() + " " +
//									c.getProgramacionDetalle().getEmpleado().getApellidoMaterno() + " " +
//									c.getProgramacionDetalle().getEmpleado().getNombres())
//							.citados(list)
//							.build();
//
//
//				return doctor;
//				});
//
//		System.out.println("================> "+atencion);

        return iCitaRepository.listaCitados( idEmpleado, numeroDiaSemana, fechaFormateada)
        		.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

//    @Override
//    public List<CitaResponse> listaHistorialCitaCliente(Integer idCliente) {
//        
//        return iCitaRepository.listaHistorialCitaCliente(idCliente)
//        		.stream().map(this::entityToResponse).collect(Collectors.toList());
//    }

    @Override
    public Page<CitaResponse> listaHistorialCitaCliente(String numeroDocumento, Pageable paging) {
        
        return iCitaRepository.listaHistorialCitaCliente(numeroDocumento, paging)
        		.map(this::entityToResponse);
    }

    @Override
    public List<Cita> listarNoAtendidos(Integer idProgramacionDetalle,Boolean atendido) {
        
        return iCitaRepository.getNoAtendidos(idProgramacionDetalle, atendido);
    }

	@Override
	public CitaResponse registrar(CitaRequest request) {
		 var obj = iCitaRepository.save(Cita.builder()
	                .horario(Horario.builder().idHorario(request.getIdHorario()).build())
	                .programacionDetalle(ProgramacionDetalle.builder()
	                        .idProgramacionDetalle(request.getIdProgramacionDetalle()).build())
	                .historiaClinica(HistoriaClinica.builder().idHistoriaClinica(request.getIdHistoriaClinica()).build())
	                .atendido(Constants.DESATENDIDO)
	                .build());
		return entityToResponse(obj);
	}

	@Override
	public CitaResponse modificar(CitaRequest request) {
		 
	        var obj = iCitaRepository.save( Cita.builder()
	                .idCita(request.getIdCita())
	                .historiaClinica(HistoriaClinica.builder().idHistoriaClinica(request.getIdHistoriaClinica()).build())
	                .programacionDetalle(ProgramacionDetalle.builder().idProgramacionDetalle(request.getIdProgramacionDetalle()).build())
	                .horario(Horario.builder().idHorario(request.getIdHorario()).build())
	                .atendido(request.getAtendido())
	                .informe(request.getInforme())
	                .build());
	        return entityToResponse(obj);
	}

	@Override
	public List<CitaResponse> listar() {
		
		return iCitaRepository.findAll()
        		.stream().map(this::entityToResponse).collect(Collectors.toList());
	}

	@Override
	public CitaResponse listarPorId(Integer id) {
		
		return entityToResponse(iCitaRepository.findById(id).get());
				
	}

	@Override
	public void eliminar(Integer id) {
		
		
	}
	
	public Cita modificarToEntity(Cita request) {
		 
  
        return iCitaRepository.save(request);
}

	@Override
	public List<Cita> listarPorFecha(String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(fecha.trim(), formatter);
		System.out.println("date === "+date);
		List<Cita> citas = iCitaRepository.listarPacientesHoy(date);

        return citas;
	}

	public List<DoctorDisponibleResponse> listarCitados(String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(fecha.trim(), formatter);
		System.out.println("date === "+date);
		List<Cita> citas = iCitaRepository.listarPacientesHoy(date);

		List<DoctorDisponibleResponse> atenciones = citas
				.stream().map(c -> {

					var list = new ArrayList<CitadosResponse>();
					var citado = CitadosResponse.builder().paciente(c.getHistoriaClinica().getApellidoPaterno() + " " +
									c.getHistoriaClinica().getApellidoMaterno() + " " +
									c.getHistoriaClinica().getNombres())
							.horario(c.getHorario().getDescripcion())
							.build();
					list.add(citado);

					var doctor =DoctorDisponibleResponse.builder().medico(c.getProgramacionDetalle().getEmpleado().getApellidoPaterno() + " " +
									c.getProgramacionDetalle().getEmpleado().getApellidoMaterno() + " " +
									c.getProgramacionDetalle().getEmpleado().getNombres())
							.citados(list)
							.build();


					return doctor;
				}).collect(Collectors.toList());

		var citados = agruparCitas(atenciones).entrySet().stream()
				.map(entry -> {
					return DoctorDisponibleResponse.builder()
							.citados(entry.getValue())
							.medico(entry.getKey())
							.build();
				}).collect(Collectors.toList());

		return citados;
	}

	public Map<String, List<CitadosResponse>> agruparCitas(List<DoctorDisponibleResponse> atenciones){
		Map<String, List<CitadosResponse>> atencionesAgrupadas = atenciones.stream()
				.collect(Collectors.groupingBy(
						DoctorDisponibleResponse::getMedico, // Clave: Nombre del médico
						Collectors.flatMapping(
								d -> d.getCitados().stream(), // Aplanar la lista de citados
								Collectors.toList() // Recolectar en una lista única
						)
				));
		System.out.println("atenciones ================> "+atencionesAgrupadas);

		return atencionesAgrupadas;
	}

	private CitaResponse entityToResponse(Cita entity) {
		System.out.println("entity == > " +entity);
		var response = new CitaResponse();
		BeanUtils.copyProperties(entity, response);
		var historiaClinica = new HistoriaClinicaResponse();
		BeanUtils.copyProperties(entity.getHistoriaClinica(), historiaClinica);
		var horarioResponse = new HorarioResponse();
		BeanUtils.copyProperties(entity.getHorario(), horarioResponse);
		var programacionDetalleResponse = new ProgramacionDetalleResponse();
		BeanUtils.copyProperties(entity.getProgramacionDetalle(), programacionDetalleResponse);
		
		if(!Objects.isNull(entity.getProgramacionDetalle().getEmpleado())) {
			
		
		var empleado = new EmpleadoResponse();
		BeanUtils.copyProperties(entity.getProgramacionDetalle().getEmpleado(), empleado);

		
		programacionDetalleResponse.setEmpleado(empleado);
		}
		response.setHistoriaClinica(historiaClinica);
		response.setHorario(horarioResponse);
		response.setProgramacionDetalle(programacionDetalleResponse);
		return response;
	}

}
