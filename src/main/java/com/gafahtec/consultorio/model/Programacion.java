package com.gafahtec.consultorio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@EqualsAndHashCode(callSuper=false)
@ToString(exclude = { "programacionDetalles" })
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Programacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProgramacion;

	private Integer idEmpresa;
	
	private Date fechaInicial;
	private Date fechaFinal;

	private String strFechaInicial;
    private String strFechaFinal;
	
	
	private String rango;

	private Boolean estado;
	
	
	
	
	@JsonIgnore
	@Builder.Default
	@OneToMany( mappedBy = "programacion", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<ProgramacionDetalle> programacionDetalles  = new ArrayList<>();


}
