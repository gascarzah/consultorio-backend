package com.gafahtec.consultorio.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gafahtec.consultorio.dto.request.TipoEmpleadoRequest;
import com.gafahtec.consultorio.dto.response.TipoEmpleadoResponse;
import com.gafahtec.consultorio.model.auth.TipoEmpleado;
import com.gafahtec.consultorio.repository.ITipoEmpleadoRepository;
import com.gafahtec.consultorio.service.ITipoEmpleadoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class TipoEmpleadoServiceImpl implements ITipoEmpleadoService {

    @Autowired
    private ITipoEmpleadoRepository iTipoEmpleadoRepository;

    @Override
    public TipoEmpleadoResponse registrar(TipoEmpleadoRequest request) {
        var obj = iTipoEmpleadoRepository.save(TipoEmpleado.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(request.getActivo() != null ? request.getActivo() : true)
                .build());
        return entityToResponse(obj);
    }

    @Override
    public TipoEmpleadoResponse modificar(TipoEmpleadoRequest request) {
        var obj = iTipoEmpleadoRepository.save(TipoEmpleado.builder()
                .idTipoEmpleado(request.getIdTipoEmpleado())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(request.getActivo())
                .build());
        return entityToResponse(obj);
    }

    @Override
    public List<TipoEmpleadoResponse> listar() {
        return iTipoEmpleadoRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TipoEmpleadoResponse listarPorId(Integer id) {
        return entityToResponse(iTipoEmpleadoRepository.findById(id).get());
    }

    @Override
    public void eliminar(Integer id) {
        iTipoEmpleadoRepository.deleteById(id);
    }

    @Override
    public Page<TipoEmpleadoResponse> listarPageable(Pageable pageable) {
        return iTipoEmpleadoRepository.findAll(pageable).map(this::entityToResponse);
    }

    @Override
    public Page<TipoEmpleadoResponse> buscarTiposEmpleado(String search, Pageable pageable) {
        System.out.println("=== BUSCAR TIPOS EMPLEADO DEBUG ===");
        System.out.println("Search term: '" + search + "'");

        Page<TipoEmpleado> tipos = iTipoEmpleadoRepository.buscarTiposEmpleado(search, pageable);
        System.out.println("Total tipos empleado found: " + tipos.getTotalElements());

        return tipos.map(this::entityToResponse);
    }

    private TipoEmpleadoResponse entityToResponse(TipoEmpleado entity) {
        var response = new TipoEmpleadoResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
