package com.gafahtec.consultorio.controller;

import com.gafahtec.consultorio.dto.request.CitaRequest;
import com.gafahtec.consultorio.dto.response.CitaResponse;
import com.gafahtec.consultorio.dto.response.CitadosResponse;
import com.gafahtec.consultorio.dto.response.DoctorDisponibleResponse;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.model.consultorio.Cita;
import com.gafahtec.consultorio.service.ICitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/citas")
@AllArgsConstructor
@Tag(name = "Cita", description = "Operaciones sobre citas médicas")
public class CitaController {

    private ICitaService iCitaService;

    @Operation(summary = "Listar todas las citas", description = "Devuelve todas las citas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<CitaResponse>> listar() throws ResourceNotFoundException {
        var lista = iCitaService.listar();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Listar citas citados", description = "Devuelve los citas citados según el empleado y el día de la semana.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listaCitados")
    public ResponseEntity<List<CitaResponse>> listaCitados(
            // @RequestParam("idEmpresa") Integer idEmpresa,
            @RequestParam("idEmpleado") Integer idEmpleado,
            @RequestParam("numeroDiaSemana") Integer numeroDiaSemana) throws ResourceNotFoundException {
        // System.out.println("idEmpresa "+ idEmpresa);
        System.out.println("idEmpleado " + idEmpleado);
        System.out.println("numeroDiaSemana " + numeroDiaSemana);

        var lista = iCitaService.listaCitados(idEmpleado, numeroDiaSemana);
        System.out.println("lista " + lista);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Listar citas por médico", description = "Devuelve las citas de un médico según el id de programación detalle.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/medico")
    public ResponseEntity<List<CitaResponse>> listarCitas(
            @RequestParam("idProgramacionDetalle") Integer idProgramacionDetalle) throws ResourceNotFoundException {
        var lista = iCitaService.listarCitas(idProgramacionDetalle);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @Operation(summary = "Obtener cita por ID", description = "Este endpoint devuelve una cita médica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> listarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        var obj = iCitaService.listarPorId(id);

        if (obj.getIdCita() == null) {
            throw new ResourceNotFoundException("Id no encontrado " + id);
        }

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Operation(summary = "Registrar nueva cita", description = "Este endpoint registra una nueva cita médica.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cita creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CitaResponse> registrar(@Valid @RequestBody CitaRequest citaRequest)
            throws ResourceNotFoundException {
        var obj = iCitaService.registrar(citaRequest);

        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @Operation(summary = "Modificar cita existente", description = "Este endpoint permite modificar los detalles de una cita médica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping
    public ResponseEntity<CitaResponse> modificar(@Valid @RequestBody CitaRequest citaRequest)
            throws ResourceNotFoundException {

        var obj = iCitaService.modificar(citaRequest);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar cita", description = "Este endpoint elimina una cita médica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cita eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        var obj = iCitaService.listarPorId(id);

        if (obj == null) {
            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
        }
        iCitaService.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Listar historial de citas de un cliente de manera paginada", description = "Este endpoint devuelve el historial de citas de un cliente paginado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/historial/pageable")
    public ResponseEntity<Page<CitaResponse>> listarPageableHistorico(
            @RequestParam("numeroDocumento") String numeroDocumento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) throws ResourceNotFoundException {
        System.out.println("numeroDocumento  " + numeroDocumento);
        System.out.println("page  " + page);
        System.out.println("size  " + size);

        Pageable paging = PageRequest.of(page, size);
        Page<CitaResponse> paginas = iCitaService.listaHistorialCitaCliente(numeroDocumento, paging);
        System.out.println("listarPageableCitas paginas " + paginas);

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }

    @Operation(summary = "Listar citas paginadas", description = "Devuelve las citas paginadas. Opcionalmente puede incluir un parámetro de búsqueda para filtrar por datos del paciente (documento, nombres, apellidos, teléfono, email).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/pageable")
    public ResponseEntity<Page<CitaResponse>> listarPageable(Pageable pageable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String search) throws ResourceNotFoundException {
        System.out.println("page  " + page);
        System.out.println("size  " + size);
        System.out.println("search  " + search);

        Page<CitaResponse> paginas;

        // Si hay un parámetro de búsqueda, usar el método de búsqueda
        if (search != null && !search.trim().isEmpty()) {
            System.out.println("=== USANDO BÚSQUEDA ===");
            System.out.println("Search parameter received: '" + search + "'");
            paginas = iCitaService.buscarCitas(search.trim(), pageable);
            System.out.println("buscarCitas paginas " + paginas);
        } else {
            System.out.println("=== USANDO LISTADO NORMAL ===");
            paginas = iCitaService.listarPageable(pageable);
            System.out.println("listarPageableCitas paginas " + paginas);
        }

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }

    @Operation(summary = "Buscar citas - Endpoint de prueba", description = "Endpoint simple para probar la búsqueda de citas.")
    @GetMapping("/buscar")
    public ResponseEntity<Page<CitaResponse>> buscarCitasSimple(
            @RequestParam String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws ResourceNotFoundException {

        System.out.println("=== ENDPOINT DE PRUEBA ===");
        System.out.println("Search: '" + search + "'");
        System.out.println("Page: " + page);
        System.out.println("Size: " + size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("programacionDetalle.fecha").descending());
        Page<CitaResponse> paginas = iCitaService.buscarCitas(search, pageable);

        System.out.println("Resultados encontrados: " + paginas.getTotalElements());

        if (paginas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paginas, HttpStatus.OK);
    }

    @Operation(summary = "Diagnóstico - Ver todas las citas", description = "Endpoint para verificar qué datos hay en la base de datos.")
    @GetMapping("/diagnostico")
    public ResponseEntity<List<CitaResponse>> diagnostico() throws ResourceNotFoundException {
        System.out.println("=== DIAGNÓSTICO DE CITAS ===");

        List<CitaResponse> todasLasCitas = iCitaService.listar();
        System.out.println("Total de citas en la base de datos: " + todasLasCitas.size());

        for (CitaResponse cita : todasLasCitas) {
            System.out.println("Cita ID: " + cita.getIdCita());
            if (cita.getHistoriaClinica() != null) {
                System.out.println("  - Documento: " + cita.getHistoriaClinica().getNumeroDocumento());
                System.out.println("  - Nombres: " + cita.getHistoriaClinica().getNombres());
                System.out.println("  - Apellido Paterno: " + cita.getHistoriaClinica().getApellidoPaterno());
                System.out.println("  - Apellido Materno: " + cita.getHistoriaClinica().getApellidoMaterno());
                System.out.println("  - Teléfono: " + cita.getHistoriaClinica().getTelefono());
                System.out.println("  - Email: " + cita.getHistoriaClinica().getEmail());
            } else {
                System.out.println("  - SIN HISTORIA CLÍNICA");
            }
            System.out.println("---");
        }

        return new ResponseEntity<>(todasLasCitas, HttpStatus.OK);
    }

    @Operation(summary = "Listar citas por fecha paginadas", description = "Devuelve las citas de una fecha específica paginadas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/pageable/{fecha}")
    public ResponseEntity<List<Cita>> listarPageablePorFecha(Pageable pageable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @PathVariable String fecha) {

        System.out.println("fecha  " + fecha);
        var obj = iCitaService.listarPorFecha(fecha);

        System.out.println("obj  " + obj);

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @Operation(summary = "Listar citas de hoy", description = "Devuelve las citas del día actual.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "204", description = "Sin contenido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/hoy/{fecha}")
    public ResponseEntity<List<DoctorDisponibleResponse>> citasHoy(@PathVariable String fecha) {

        System.out.println("fecha  " + fecha);

        List<DoctorDisponibleResponse> obj = iCitaService.listarCitados(fecha);
        System.out.println("obj  " + obj);

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
