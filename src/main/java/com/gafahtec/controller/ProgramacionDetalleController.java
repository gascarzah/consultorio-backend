package com.gafahtec.controller;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gafahtec.dto.request.ProgramacionDetalleRequest;
import com.gafahtec.dto.response.ProgramacionDetalleHelperResponse;
import com.gafahtec.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.exception.ResourceNotFoundException;
import com.gafahtec.service.IProgramacionDetalleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/programacionesDetalladas")
@AllArgsConstructor
@Log4j2
public class ProgramacionDetalleController {

	private IProgramacionDetalleService iProgramacionDetalleService;

	@PostMapping
	public ResponseEntity<Set<ProgramacionDetalleResponse>> registrar(
			@Valid @RequestBody ProgramacionDetalleRequest programacionDetalleRequest) throws Exception {

		if (!iProgramacionDetalleService.existeProgramacionEmpleado(programacionDetalleRequest)) {
			throw new ResourceNotFoundException(
					"El empleado " + programacionDetalleRequest.getNumeroDocumento() + " ya tiene programacion");
		}

		var obj = iProgramacionDetalleService.registrar(programacionDetalleRequest);
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ProgramacionDetalleResponse> modificar(
			@Valid @RequestBody ProgramacionDetalleRequest programacionDetalleRequest) throws Exception {

		System.out.println(programacionDetalleRequest);
		var obj = iProgramacionDetalleService.modificar(programacionDetalleRequest);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/pageable")
	public ResponseEntity<Page<ProgramacionDetalleResponse>> listarPageable(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) throws Exception {

		Pageable paging = PageRequest.of(page, size, Sort.by("numeroDiaSemana").descending());
		Page<ProgramacionDetalleResponse> paginas = iProgramacionDetalleService.listarPageable(paging);

		if (paginas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(paginas, HttpStatus.OK);
	}

	@GetMapping("/programaDetalle/{id}")
	public ResponseEntity<ProgramacionDetalleHelperResponse> listarPorId(@PathVariable("id") Integer idProgramacion)
			throws Exception {

		var obj = iProgramacionDetalleService.listarPorIdProgramacion(idProgramacion);

		if (obj.getEmpleado() == null) {
			throw new ResourceNotFoundException("Id no encontrado " + idProgramacion);
		}

		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@GetMapping("/listarDiasProgramados")
	public ResponseEntity<Set<ProgramacionDetalleResponse>> listarDiasProgramados(
			@RequestParam("numeroDocumento") String numeroDocumento, @RequestParam("idEmpresa") Integer idEmpresa)
			throws Exception {

		var lista = iProgramacionDetalleService.listarDiasProgramados(numeroDocumento, idEmpresa);

		if (lista.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	//////////////////////

//    @GetMapping("/medico/dia")
//    public ResponseEntity<Set<ProgramacionDetalleResponse>> citasPendientes(@RequestParam("idMedico") Integer idMedico,
//            @RequestParam("numeroDiaSemana") Integer numeroDiaSemana)
//            throws Exception {
//       
//
//        var lista = iProgramacionDetalleService.citasPendientes(idMedico, numeroDiaSemana);
//
//        if (lista.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(lista, HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
//        var obj = iProgramacionDetalleService.listarPorId(id);
//
//        if (obj.getIdProgramacionDetalle()== null) {
//            throw new ResourceNotFoundException("ID NO ENCONTRADO " + id);
//        }
//
//        iProgramacionService.eliminar(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
