package com.gafahtec.repository;

import org.springframework.stereotype.Repository;

import com.gafahtec.model.auth.Menu;

@Repository
public interface IMenuRepository extends IGenericRepository<Menu,Integer>{
}
