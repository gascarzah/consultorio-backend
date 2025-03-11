package com.gafahtec.consultorio.model.consultorio;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.*;

@Setter
@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HistoriaClinica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String idHistoriaClinica;

	private String ectoscopia;
	private String alergia;
	private String motivo;
	private String antecedentesMedicos;

	@MapsId
	@JoinColumn(name = "numero_documento")
	@OneToOne(fetch = FetchType.LAZY) // Mantengo LAZY ya que es una relación uno a uno
	private Cliente cliente;
}
