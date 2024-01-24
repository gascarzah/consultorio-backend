package com.gafahtec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gafahtec.dto.request.MenuRequest;
import com.gafahtec.dto.response.MenuResponse;
import com.gafahtec.model.auth.Menu;


public interface IMenuService extends ICRUD<MenuRequest, MenuResponse,Integer>{
    Page<MenuResponse> listarPageable(Pageable pageable);
}
