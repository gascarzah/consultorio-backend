package com.gafahtec.consultorio.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class MenusPorRolResponse implements Serializable {
	private Integer idMenu;
	private String nombre;
	private Boolean activo;
}
