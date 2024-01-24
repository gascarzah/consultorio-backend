package com.gafahtec.model.auth;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoId implements Serializable{

	private static final long serialVersionUID = -4743320433937732701L;
	@Column(name = "numero_documento")
    private String numeroDocumento;
	@Column(name = "id_empresa")
    private Integer idEmpresa;
}
