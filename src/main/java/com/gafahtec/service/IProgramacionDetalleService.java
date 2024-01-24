package com.gafahtec.service;

import java.text.ParseException;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.ProgramacionDetalleRequest;
import com.gafahtec.dto.response.ProgramacionDetalleHelperResponse;
import com.gafahtec.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.model.consultorio.ProgramacionDetalle;

import jakarta.validation.Valid;

public interface IProgramacionDetalleService  {


	Set<ProgramacionDetalleResponse> registrar(ProgramacionDetalleRequest request) ;
	

	ProgramacionDetalleResponse modificar(@Valid ProgramacionDetalleRequest programacionDetalleRequest);


	void eliminar(Integer id);
	
	Page<ProgramacionDetalleResponse> listarPageable(Pageable pageable);
	
	Set<ProgramacionDetalleResponse> getProgramacionEmpleado(ProgramacionDetalleRequest programacionDetalleRequest);
	
	Boolean existeProgramacionEmpleado(ProgramacionDetalleRequest request);
    
	
	Set<ProgramacionDetalleResponse> listarDiasProgramados(String numeroDocumento, Integer idEmpresa) throws ParseException;

	Set<ProgramacionDetalle> getProgramacionDetalleActivo(Boolean estado);
	public ProgramacionDetalle modificarEntity(ProgramacionDetalle programacionDetalle);
    //////////////////////////////////////////////
	


	

	Set<ProgramacionDetalleResponse> verificaProgramacion(Integer idMedico, String fechaInicial, String fechaFinal);

	Set<ProgramacionDetalleResponse> citasPendientes(Integer idMedico, Integer numeroDiaSemana);

    

	ProgramacionDetalleHelperResponse listarPorIdProgramacion(Integer idProgramacion);



   




    

   
}
