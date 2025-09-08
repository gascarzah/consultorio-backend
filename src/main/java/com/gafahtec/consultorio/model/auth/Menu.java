package com.gafahtec.consultorio.model.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMenu;
	private String nombre;
	private String path;
	private Boolean activo;
}
