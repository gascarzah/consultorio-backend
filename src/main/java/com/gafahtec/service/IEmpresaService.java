package com.gafahtec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.EmpresaRequest;
import com.gafahtec.dto.response.EmpresaResponse;

public interface IEmpresaService extends ICRUD<EmpresaRequest,EmpresaResponse , Integer>{
    Page<EmpresaResponse> listarPageable(Pageable pageable);
}
