package com.gafahtec.consultorio.repository;

import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.Paciente;

@Repository
public interface IPacienteRepository extends IGenericRepository<Paciente,Integer>{
}
