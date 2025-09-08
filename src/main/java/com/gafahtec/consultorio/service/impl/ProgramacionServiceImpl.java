package com.gafahtec.consultorio.service.impl;

import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.gafahtec.consultorio.dto.Semana;
import com.gafahtec.consultorio.util.GenerarProgramacionFechas;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.util.CollectionUtils;

import static com.gafahtec.consultorio.util.GenerarProgramacionFechas.obtenerNumeroSemana;

@AllArgsConstructor
@Service
@Transactional
@Log4j2
public class ProgramacionServiceImpl implements IProgramacionService {


    private IProgramacionRepository iProgramacionRepository;


    @Override
    public List<ProgramacionResponse> listarPorRango(String rango) {
        return iProgramacionRepository.findByRango(rango).stream().map(this::entityToResponse).collect(Collectors.toList());
    }


    @Override
    public Page<ProgramacionResponse> listarPageable(Pageable pageable) {
        return iProgramacionRepository.findAll(pageable).map(this::entityToResponse);
    }


    @Override
    public Page<ProgramacionResponse> listarProgramacionPageable(Integer idEmpresa, Pageable pageable) {
        return iProgramacionRepository.listarProgramacionPageable(idEmpresa, pageable).map(this::entityToResponse);
    }


    @Override
    public ProgramacionResponse registrar(ProgramacionRequest request) {

        String strFechaInicial = Utils.getFecha2String(request.getFechaInicial());
        String strFechaFinal = Utils.getFecha2String(request.getFechaFinal());


        String rango = strFechaInicial + " - " + strFechaFinal;

        List<Programacion> listaProgramacion = iProgramacionRepository.findByRango(rango);

        if(CollectionUtils.isEmpty(listaProgramacion)){

            var programacion = new Programacion();
            BeanUtils.copyProperties(request, programacion);
            programacion.setRango(rango);
            programacion.setActivo(Constants.ACTIVO);
            programacion.setStrFechaFinal(strFechaFinal);
            programacion.setStrFechaInicial(strFechaInicial);

            var obj = iProgramacionRepository.save(programacion);
            return entityToResponse(obj);
        }

//        log.warn("⚠️ Ya existe programación para el rango: {}", rango);
return null;


    }


    @Override
    public ProgramacionResponse modificar(ProgramacionRequest request) {
        var programacion = new Programacion();
        BeanUtils.copyProperties(request, programacion);
        var obj = modificarEntity(programacion);
        return entityToResponse(obj);
    }


    public Programacion modificarEntity(Programacion request) {
        return iProgramacionRepository.save(request);
    }


    @Override
    public List<ProgramacionResponse> listar() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public ProgramacionResponse listarPorId(Integer id) {
        return entityToResponse(iProgramacionRepository.findById(id).get());
    }


    @Override
    public void eliminar(Integer id) {
        iProgramacionRepository.deleteById(id);

    }

    private ProgramacionResponse entityToResponse(Programacion entity) {
        var response = new ProgramacionResponse();
        BeanUtils.copyProperties(entity, response);


        return response;
    }


    @Override
    public List<ProgramacionResponse> programacionActivo() {

        List<Programacion> programacionActiva = programacionEntityActivo();
        if (!programacionActiva.isEmpty()) {
            Programacion programacion = programacionActiva.iterator().next();
            var response = entityToResponse(programacion);
            var nuevoSet = new ArrayList<ProgramacionResponse>();
            nuevoSet.add(response);
            return nuevoSet;
        }
        return Collections.emptyList();
    }

    public List<Programacion> programacionEntityActivo() {
        return iProgramacionRepository.findByActivoOrderByFechaInicial(Constants.ACTIVO);
    }

    public List<Programacion> findByActivo() {
        return iProgramacionRepository.findByActivo(Constants.ACTIVO);
    }

}
