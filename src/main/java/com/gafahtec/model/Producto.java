//package com.gafahtec.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ForeignKey;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
////@Data
////@Entity
////@Table
////@AllArgsConstructor
////@NoArgsConstructor
////@Builder
////@ToString(exclude = { "productoDetalles", "pedidoDetalles" })
//////@ToString(exclude = {"categoriaProducto","destino", "productoDetalles", "pedidoDetalles" })
//public class Producto {
////	@Id
////	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer idProducto;
////	@Schema(description = "nombre del producto")
////	@Size(min=3, message = "{persona.nombres}" )
////	@Column(name = "nombre", nullable = false, length = 100)
//	private String nombre;
//	private String descripcion;
//	private Float precio;
//	private Integer stock;
////	@ManyToOne
////	@JoinColumn(name = "id_categoria_producto", nullable = false, foreignKey = @ForeignKey(name = "FK_producto_categoria"))
//	private String categoria;
//	private String imagen;
////	@ManyToOne
////	@JoinColumn(name = "id_destino", nullable = true, foreignKey = @ForeignKey(name = "FK_destino_producto"))
////	private Destino destino;
//	private String randomId;
//	
////	@JsonIgnore
////	@Builder.Default
////	@OneToMany(mappedBy = "producto", cascade = { CascadeType.ALL }, orphanRemoval = true)
////	private List<ProductoDetalle> productoDetalles  = new ArrayList<>();
////	
////	@JsonIgnore
////	@Builder.Default
////	@OneToMany(mappedBy = "producto", cascade = { CascadeType.ALL }, orphanRemoval = true)
////	private List<PedidoDetalle> pedidoDetalles  = new ArrayList<>();
//	
//	private String idVendedor;
//	
//	private String marca;
//	private String ofreceEnvio;
//	private String detalle;
//	
//	
//}
