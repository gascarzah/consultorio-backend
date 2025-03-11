package com.gafahtec.consultorio.repository;

import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.consultorio.Cliente;

@Repository
public interface IClienteRepository extends IGenericRepository<Cliente,String>{
}
