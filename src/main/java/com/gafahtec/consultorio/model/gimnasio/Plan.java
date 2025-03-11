//package com.gafahtec.consultorio.model.gimnasio;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
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
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class Plan {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer idPlan;
//	private String nombre;
//	private Integer dias;
//	private Double precio;
//	
//	@JsonIgnore
//	@Builder.Default
//	@OneToMany(mappedBy = "plan", cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
//	private Set<Matricula> matriculas = new HashSet<>();
//	
////	@JsonIgnore
////	@Builder.Default
////	@OneToMany(mappedBy = "plan", cascade = { CascadeType.ALL }, orphanRemoval = true)
////	private List<Venta> ventas = new ArrayList<>();;
//}
