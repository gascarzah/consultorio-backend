package com.gafahtec.consultorio.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.CitaRequest;
import com.gafahtec.consultorio.dto.response.CitaResponse;
import com.gafahtec.consultorio.dto.response.ClienteResponse;
import com.gafahtec.consultorio.dto.response.EmpleadoResponse;
import com.gafahtec.consultorio.dto.response.EmpresaResponse;
import com.gafahtec.consultorio.dto.response.HorarioResponse;
import com.gafahtec.consultorio.dto.response.PersonaResponse;
import com.gafahtec.consultorio.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.consultorio.Cita;
import com.gafahtec.consultorio.model.consultorio.Cliente;
import com.gafahtec.consultorio.model.consultorio.Horario;
import com.gafahtec.consultorio.model.consultorio.ProgramacionDetalle;
import com.gafahtec.consultorio.repository.ICitaRepository;
import com.gafahtec.consultorio.repository.IHorarioRepository;
import com.gafahtec.consultorio.service.ICitaService;
import com.gafahtec.consultorio.util.Constants;

import lombok.AllArgsConstructor;

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
    public Set<CitaResponse> listarCitas(Integer idProgramacionDetalle) {
        
        return iCitaRepository.findByProgramacionDetalleOrderByCita(idProgramacionDetalle)
        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
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
    public Set<CitaResponse> listaCitados(Integer idEmpresa,String numeroDocumento, Integer numeroDiaSemana) {
        
        return iCitaRepository.listaCitados(idEmpresa, numeroDocumento, numeroDiaSemana)
        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

//    @Override
//    public Set<CitaResponse> listaHistorialCitaCliente(Integer idCliente) {
//        
//        return iCitaRepository.listaHistorialCitaCliente(idCliente)
//        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
//    }

    @Override
    public Page<CitaResponse> listaHistorialCitaCliente(String numeroDocumento, Pageable paging) {
        
        return iCitaRepository.listaHistorialCitaCliente(numeroDocumento, paging)
        		.map(this::entityToResponse);
    }

    @Override
    public Set<Cita> listarNoAtendidos(Integer idProgramacionDetalle,Boolean atendido) {
        
        return iCitaRepository.getNoAtendidos(idProgramacionDetalle, atendido);
    }

	@Override
	public CitaResponse registrar(CitaRequest request) {
		 var obj = iCitaRepository.save(Cita.builder()
	                .horario(Horario.builder().idHorario(request.getIdHorario()).build())
	                .programacionDetalle(ProgramacionDetalle.builder()
	                        .idProgramacionDetalle(request.getIdProgramacionDetalle()).build())
	                .cliente(Cliente.builder().numeroDocumento(request.getNumeroDocumento()).build())
	                .atendido(Constants.DESATENDIDO)
	                .build());
		return entityToResponse(obj);
	}

	@Override
	public CitaResponse modificar(CitaRequest request) {
		 
	        var obj = iCitaRepository.save( Cita.builder()
	                .idCita(request.getIdCita())
	                .cliente(Cliente.builder().numeroDocumento(request.getNumeroDocumento()).build())
	                .programacionDetalle(ProgramacionDetalle.builder().idProgramacionDetalle(request.getIdProgramacionDetalle()).build())
	                .horario(Horario.builder().idHorario(request.getIdHorario()).build())
	                .atendido(request.getAtendido())
	                .informe(request.getInforme())
	                .build());
	        return entityToResponse(obj);
	}

	@Override
	public Set<CitaResponse> listar() {
		
		return iCitaRepository.findAll()
        		.stream().map(this::entityToResponse).collect(Collectors.toSet());
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
	
	private CitaResponse entityToResponse(Cita entity) {
		System.out.println("entity == > " +entity);
		var response = new CitaResponse();
		BeanUtils.copyProperties(entity, response);
		var clienteResponse = new ClienteResponse();
		BeanUtils.copyProperties(entity.getCliente(), clienteResponse);
		var horarioResponse = new HorarioResponse();
		BeanUtils.copyProperties(entity.getHorario(), horarioResponse);
		var programacionDetalleResponse = new ProgramacionDetalleResponse();
		BeanUtils.copyProperties(entity.getProgramacionDetalle(), programacionDetalleResponse);
		
		if(!Objects.isNull(entity.getProgramacionDetalle().getEmpleado())) {
			
		
		var empleado = new EmpleadoResponse();
		BeanUtils.copyProperties(entity.getProgramacionDetalle().getEmpleado(), empleado);
		
//		var persona = new PersonaResponse();
//		BeanUtils.copyProperties(entity.getProgramacionDetalle().getEmpleado().getPersona(), persona);
//		empleado.setPersona(persona);
//
//		var empresa = new EmpresaResponse();
//		BeanUtils.copyProperties(entity.getProgramacionDetalle().getEmpleado().getEmpresa(), empresa);
//		empleado.setEmpresa(empresa);
		
//		empleado.setIdEmpresa(empresa.getIdEmpresa());
//		empleado.setNumeroDocumento(persona.getNumeroDocumento());
		
		programacionDetalleResponse.setEmpleado(empleado);
		}
		response.setCliente(clienteResponse);
		response.setHorario(horarioResponse);
		response.setProgramacionDetalle(programacionDetalleResponse);
		return response;
	}

}
