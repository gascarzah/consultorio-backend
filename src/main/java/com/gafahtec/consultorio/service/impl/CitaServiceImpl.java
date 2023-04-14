package com.gafahtec.consultorio.service.impl;

import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.Cita;
import com.gafahtec.consultorio.model.Horario;
import com.gafahtec.consultorio.model.ProgramacionDetalle;
import com.gafahtec.consultorio.repository.ICitaRepository;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.service.ICitaService;
import com.gafahtec.consultorio.service.IHorarioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class CitaServiceImpl extends CRUDImpl<Cita, Integer> implements ICitaService {

    private ICitaRepository repo;

    private IHorarioService iHorarioService;

    @Override
    protected IGenericRepository<Cita, Integer> getRepo() {

        return repo;
    }

    @Override
    public Page<Cita> listarPageable(Pageable pageable) throws ResourceNotFoundException{
        return repo.findAll(pageable);
    }

    public void registrarHorarios(List<ProgramacionDetalle> list) {

       
            List<Horario> horarios = iHorarioService.listar();

            for (ProgramacionDetalle programacionDetalle : list) {

                for (Horario horario : horarios) {

                    repo.save(Cita.builder()
                            .programacionDetalle(programacionDetalle)
                            .horario(horario)
                            .atendido(0)
                            .build());
                }

            }

        

    }

    @Override
    public List<Cita> listarCitas(Integer idProgramacionDetalle) {
        // TODO Auto-generated method stub
        return repo.findByProgramacionDetalleOrderByCita(idProgramacionDetalle);
    }

    @Transactional
    @Override
    public Integer eliminar(Integer idCita, Integer idHorario, Integer idProgramacionDetalle) {
        // TODO Auto-generated method stub
        return repo.eliminar(idCita, idHorario, idProgramacionDetalle);
    }

    @Transactional
    @Override
    public Integer updateAtencion(Integer idCita) {
        // TODO Auto-generated method stub
        return repo.updateAtencion(idCita);
    }

    @Override
    public List<Cita> listaCitados(Integer idMedico, Integer numeroDiaSemana) {
        // TODO Auto-generated method stub
        return repo.listaCitados(idMedico,  numeroDiaSemana);
    }

    @Override
    public List<Cita> listaHistorialCitaCliente(Integer idCliente) {
        // TODO Auto-generated method stub
        return repo.listaHistorialCitaCliente(idCliente);
//        return repo.findByCliente(Cliente.builder().idCliente(idCliente).build());
    }

    @Override
    public Page<Cita> listaHistorialCitaCliente(Integer idCliente, Pageable paging) {
        // TODO Auto-generated method stub
//        return repo.findByClienteNoAtendidos(Cliente.builder().idCliente(idCliente).build(), paging);
        return repo.findByClienteNoAtendidos(idCliente, paging);
    }

    @Override
    public List<Cita> listarNoAtendidos(Integer idProgramacionDetalle,Integer atendido) {
        // TODO Auto-generated method stub
        return repo.getNoAtendidos(idProgramacionDetalle, atendido);
    }

}
