package com.gafahtec.consultorio.service.impl;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.ProgramacionDetalleRequest;
import com.gafahtec.consultorio.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.consultorio.model.Empleado;
import com.gafahtec.consultorio.model.Programacion;
import com.gafahtec.consultorio.model.ProgramacionDetalle;
import com.gafahtec.consultorio.repository.IGenericRepository;
import com.gafahtec.consultorio.repository.IProgramacionDetalleRepository;
import com.gafahtec.consultorio.service.IProgramacionDetalleService;
import com.gafahtec.consultorio.util.Constants;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Service
@Log4j2
@Transactional
public class ProgramacionDetalleServiceImpl extends CRUDImpl<ProgramacionDetalle, Integer>
        implements IProgramacionDetalleService {

    private IProgramacionDetalleRepository repo;

    @Override
    protected IGenericRepository<ProgramacionDetalle, Integer> getRepo() {

        return repo;
    }

//    public List<ProgramacionDetalle> generarProgramacionDetalle(Programacion programacion,
//            ProgramacionRequest programacionRequest) {
//
//        List<ProgramacionDetalle> list = new ArrayList<>();
//        try {
//            list = generarProgramacion(programacion, programacionRequest);
//
//        } catch (ParseException e) {
//            log.info("msg", "Se producjo un problema al generar horario");
//            log.error("error", e);
//
//        }
//
//        return list;
//
//    }

    @Override
    public List<ProgramacionDetalle> generarProgramacionDetalle(Programacion programacion,
            ProgramacionDetalleRequest programacionDetalleRequest) throws ParseException {

        LocalDate startLocalDate = programacion.getFechaInicial().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        System.out.println("startLocalDate " + startLocalDate);
        LocalDate endLocalDate = programacion.getFechaFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("endLocalDate " + endLocalDate);

        List<ProgramacionDetalle> programacionDetalleList = new ArrayList<>();

        for (String dia : programacionDetalleRequest.getChecked()) {
            if (StringUtils.isNotEmpty(dia)) {
                LocalDate diaProgramado = startLocalDate.plusDays(Integer.parseInt(dia));
                DayOfWeek dayOfWeek = diaProgramado.getDayOfWeek();

                ProgramacionDetalle programacionDetalle = getRepo().save(ProgramacionDetalle.builder()
                        .programacion(programacion)
                        .fecha(diaProgramado).diaSemana(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))
                        .numeroDiaSemana(dayOfWeek.getValue())
                        .empleado(Empleado.builder().idEmpleado(programacionDetalleRequest.getIdEmpleado()).build())
                        .estado(Constants.ACTIVO)
//                        .progActiva(true)
                        .build());

                log.info("Resultado", programacionDetalle);
                programacionDetalleList.add(programacionDetalle);
            }

        }

        return programacionDetalleList;
    }

//    @Override
//    public List<ProgramacionDetalle> generarProgramacion(Programacion programacion,
//            ProgramacionRequest programacionRequest) throws ParseException {
//
//        LocalDate startLocalDate = programacion.getFechaInicial().toInstant().atZone(ZoneId.systemDefault())
//                .toLocalDate();
//        System.out.println("startLocalDate "+startLocalDate);
//        LocalDate endLocalDate = programacion.getFechaFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        System.out.println("endLocalDate "+endLocalDate);
//        
//        List<ProgramacionDetalle> programacionDetalleList = new ArrayList<>();
//
//        for (String dia : programacionRequest.getChecked()) {
//            LocalDate diaProgramado = startLocalDate.plusDays(Integer.parseInt(dia));
//            DayOfWeek dayOfWeek = diaProgramado.getDayOfWeek();
//
//            ProgramacionDetalle programacionDetalle = getRepo().save(ProgramacionDetalle.builder()
//                    .programacion(programacion)
//                    .fecha(diaProgramado).diaSemana(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))
//                    .numeroDiaSemana(dayOfWeek.getValue())
////                    .empleado(Empleado.builder().idEmpleado(programacionRequest.getIdEmpleado()).build())
//                    .estado(0)
//                    .build());
//
//            log.info("Resultado", programacionDetalle);
//            programacionDetalleList.add(programacionDetalle);
//        }
//
//        return programacionDetalleList;
//    }

//    @Override
//    public List<ProgramacionDetalle> generarProgramacion(Programacion programacion,
//            ProgramacionRequest programacionRequest) throws ParseException {
//
//        LocalDate startLocalDate = programacion.getFechaInicial().toInstant().atZone(ZoneId.systemDefault())
//                .toLocalDate();
//        System.out.println("startLocalDate "+startLocalDate);
//        LocalDate endLocalDate = programacion.getFechaFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        System.out.println("endLocalDate "+endLocalDate);
//        
//        List<ProgramacionDetalle> programacionDetalleList = new ArrayList<>();
//
//        for (String dia : programacionRequest.getChecked()) {
//            LocalDate diaProgramado = startLocalDate.plusDays(Integer.parseInt(dia));
//            DayOfWeek dayOfWeek = diaProgramado.getDayOfWeek();
//
//            ProgramacionDetalle programacionDetalle = getRepo().save(ProgramacionDetalle.builder()
//                    .programacion(programacion)
//                    .fecha(diaProgramado).diaSemana(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))
//                    .numeroDiaSemana(dayOfWeek.getValue())
////                    .empleado(Empleado.builder().idEmpleado(programacionRequest.getIdEmpleado()).build())
//                    .estado(0)
//                    .build());
//
//            log.info("Resultado", programacionDetalle);
//            programacionDetalleList.add(programacionDetalle);
//        }
//
//        return programacionDetalleList;
//    }

    @Override
    public List<ProgramacionDetalle> generarDiasProgramados(Integer idMedico) throws ParseException {

        return repo.findByEmpleadoAndPendiente(idMedico, 0);
    }

    public List<ProgramacionDetalle> getProgramacionMedico(Integer idProgramacion, Integer idMedico) {

        return repo.getProgramacionMedico(idProgramacion, idMedico);
    }

    @Override
    public List<ProgramacionDetalle> programacionDias(Boolean estado) {

        return repo.findByEstado(estado);
    }

    @Override
    public List<ProgramacionDetalle> verificaProgramacion(Integer idMedico, String fechaInicial, String fechaFinal) {
        return repo.verificaProgramacion(idMedico, fechaInicial, fechaFinal);

    }

    @Override
    public List<ProgramacionDetalle> citasPendientes(Integer idMedico, Integer numeroDiaSemana) {

        return repo.citasPendientes(idMedico, Constants.ACTIVO, numeroDiaSemana);
    }

    @Override
    public Page<ProgramacionDetalle> listarPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public ProgramacionDetalleResponse listarPorIdProgramacion(Integer idProgramacion) {
        List<ProgramacionDetalle> lista = repo
                .findByProgramacion(Programacion.builder().idProgramacion(idProgramacion).build());

        Set<Empleado> hset = new HashSet<>();
        List<Boolean> listDias = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listDias.add(false);
        }
        for (ProgramacionDetalle pd : lista) {
            hset.add(pd.getEmpleado());

            if (pd.getEstado()) {
                listDias.set((pd.getNumeroDiaSemana() - 1), true);
            }
        }

        System.out.println(listDias);

        if (hset.size() > 0) {
            List<Empleado> list = new ArrayList<>(hset);
            return ProgramacionDetalleResponse.builder().empleado(list.get(0)).programacionDetalles(lista)
                    .listaDias(listDias).build();

        }

        return null;
    }

    @Override
    public List<ProgramacionDetalle> listarPorProgramacion(Integer idProgramacion) {

        return repo.findByProgramacion(Programacion.builder().idProgramacion(idProgramacion).build());
    }

    @Override
    public Page<ProgramacionDetalle> listarProgramacionEmpleadoPageable(Pageable pageable) {
        return repo.listarProgramacionEmpleadoPageable(pageable);
    }

}
