//package com.gafahtec.consultorio.model.gimnasio;
//
//import java.time.LocalDateTime;
//
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Setter
//@Getter
//@Entity
//@Table
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
//@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//public class Visita {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer idVisita;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Matricula matricula;
//	
//	private LocalDateTime fecha;
//}
