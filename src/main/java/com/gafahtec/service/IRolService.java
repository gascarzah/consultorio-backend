package com.gafahtec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.RolRequest;
import com.gafahtec.dto.response.RolResponse;

public interface IRolService extends ICRUD<RolRequest, RolResponse , Integer>{

    Page<RolResponse> listarPageable(Pageable pageable);

}
