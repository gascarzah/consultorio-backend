package com.gafahtec.repository;

import org.springframework.stereotype.Repository;

import com.gafahtec.model.consultorio.Cliente;

@Repository
public interface IClienteRepository extends IGenericRepository<Cliente,String>{
}
