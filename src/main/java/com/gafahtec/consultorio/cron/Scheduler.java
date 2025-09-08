package com.gafahtec.consultorio.cron;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.gafahtec.consultorio.dto.Semana;
import com.gafahtec.consultorio.dto.request.ProgramacionDetalleRequest;
import com.gafahtec.consultorio.dto.request.ProgramacionRequest;
import com.gafahtec.consultorio.dto.response.ProgramacionDetalleResponse;
import com.gafahtec.consultorio.dto.response.ProgramacionResponse;
import com.gafahtec.consultorio.exception.ResourceNotFoundException;
import com.gafahtec.consultorio.repository.IProgramacionDetalleRepository;
import com.gafahtec.consultorio.repository.IProgramacionRepository;
import com.gafahtec.consultorio.util.GenerarProgramacionFechas;
import com.gafahtec.consultorio.util.Utils;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gafahtec.consultorio.model.consultorio.Cita;
import com.gafahtec.consultorio.model.consultorio.Programacion;
import com.gafahtec.consultorio.model.consultorio.ProgramacionDetalle;
import com.gafahtec.consultorio.service.ICitaService;
import com.gafahtec.consultorio.service.IProgramacionDetalleService;
import com.gafahtec.consultorio.service.IProgramacionService;
import com.gafahtec.consultorio.util.Constants;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gafahtec.consultorio.util.GenerarProgramacionFechas.obtenerNumeroSemana;

@Component
@AllArgsConstructor
@Log4j2
public class Scheduler {

    IProgramacionService iProgramacionService;
    IProgramacionDetalleService iProgramacionDetalleService;
    ICitaService iCitaService;


    @Scheduled(cron = "${cron.expression}")
    public void ejecutar() throws Exception {
        scheduleTask();
        registrarProgramacionAutomatica();
        verificarYGenerarTercerMes();
    }


//    @Scheduled(cron = "${cron.expression}")
    public void scheduleTask() throws Exception {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        ZoneId systemTimeZone = ZoneId.systemDefault();
        Date fechaActual = new Date();
//          System.out.println(" fechaActual " + fechaActual);

        String strFechaActual = simpleDateFormat.format(fechaActual);

        List<Programacion> listaProgramacion = iProgramacionService.programacionEntityActivo();

        // Iterar sobre cada programación
        for (Programacion programacion : listaProgramacion) {
            // Verificar si la fecha actual ya ha pasado la fecha final de la programación
            if (fechaActual.after(programacion.getFechaFinal())) {
                // Si la fecha actual ya pasó la fecha final de la programación, inactivarla
                programacion.setActivo(Constants.INACTIVO); // Cambiar el estado a inactivo
                try {
                    // Modificar la entidad (actualizarla en la base de datos)
                    iProgramacionService.modificarEntity(programacion);
//                    System.out.println("Programación inactivada: " + programacion);
                } catch (Exception e) {
                    // Si ocurre algún error, imprimir el stack trace
                    e.printStackTrace();
                }
            } else {
                // Si la fecha actual aún está dentro del rango o es futura, la programación sigue activa
//                System.out.println("Programación sigue activa: " + programacion);
            }
        }


        List<ProgramacionDetalle> listaProgramacionDetalle = iProgramacionDetalleService.getProgramacionDetalleActivo(Constants.ACTIVO);
//          System.out.println(" listaProgramacionDetalle " + listaProgramacionDetalle);

        listaProgramacionDetalle.forEach(det -> {
            ZonedDateTime zonedDateTime = det.getFecha().atStartOfDay(systemTimeZone);
            Date utilDate = Date.from(zonedDateTime.toInstant());
//              System.out.println("fechaActual "+fechaActual + " utilDate "+utilDate);
            String strFechaProgramacion = simpleDateFormat.format(utilDate);
//              System.out.println(" strFechaActual ============ strFechaProgramacion");
//              System.out.println(strFechaActual + " ========== "+ strFechaProgramacion);
            if (fechaActual.after(utilDate) && !StringUtils.equals(strFechaActual, strFechaProgramacion)) {
//                  System.out.println("fechaActual "+fechaActual + " utilDate "+utilDate);
//                  System.out.println(" utilDate "+utilDate);
//                  System.out.println(det.getFecha());
                det.setActivo(Constants.INACTIVO);
                actualizarCitaDelDia(det.getIdProgramacionDetalle());
                try {
                    iProgramacionDetalleService.modificarEntity(det);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    
    
    public void actualizarCitaDelDia(Integer idProgramacionDetalle) {
        
        List<Cita> citas = iCitaService.listarNoAtendidos(idProgramacionDetalle, Constants.DESATENDIDO);
        
        for(Cita cita : citas) {
            if(null == cita.getHistoriaClinica()) {
                cita.setEstado(3); //no programado
            }else {
                cita.setEstado(2);//no asistio
            }
//            System.out.println("valor cita "+ cita);
            iCitaService.modificarToEntity(cita);
        }
    }
    
    public void registrarProgramacionAutomatica() {
        int anioActual = Year.now().getValue();

        List<Semana> rangoSemanal = GenerarProgramacionFechas.semanasDelAnio(anioActual);

        rangoSemanal.forEach( s-> {
            Date inicio = Date.from(s.getInicio().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fin = Date.from(s.getFin().atStartOfDay(ZoneId.systemDefault()).toInstant());
            var request =ProgramacionRequest.builder()
                    .fechaInicial(inicio)
                    .fechaFinal(fin)
                    .numeroSemana(obtenerNumeroSemana(s.getInicio()))
                    .build();
            var programacion = iProgramacionService.registrar(request);
//            log.info(programacion);
        });
    }


//    public void registrarProgramacionDetalleAutomatica(
//            @Valid @RequestBody ProgramacionDetalleRequest programacionDetalleRequest) throws Exception {
//
//        if (!iProgramacionDetalleService.existeProgramacionEmpleado(programacionDetalleRequest)) {
//            throw new ResourceNotFoundException("El empleado "+ programacionDetalleRequest.getIdEmpleado()+" ya tiene programacion");
//        }
//
//        var obj =iProgramacionDetalleService.registrar(programacionDetalleRequest);
//
//    }

    public void verificarYGenerarTercerMes() {
       iProgramacionDetalleService.generarProgramacionAutomaticaCada3Meses();
    }


}
