//package com.gafahtec.consultorio.model.gimnasio;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.gafahtec.consultorio.model.Cliente;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.EqualsAndHashCode;
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
//@ToString(exclude = { "visitas" })
//@EqualsAndHashCode(exclude = { "visitas" })
//@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//
//public class Matricula {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer idMatricula;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Plan plan;
//	@ManyToOne(fetch = FetchType.LAZY)
//	private Cliente cliente;
//	
//	private Integer idEmpresa;
//
//	private LocalDateTime fechaInicio;
//	private LocalDateTime fechaFin;
//
//	
//	private Boolean activo;
//
//	@JsonIgnore
//	@Builder.Default
//	@OneToMany(mappedBy = "matricula", cascade = { CascadeType.ALL }, orphanRemoval = true)
//	private Set<Visita> visitas = new HashSet<>();
//}
