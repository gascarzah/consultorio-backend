package com.gafahtec.consultorio.cron;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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

@Component
@AllArgsConstructor
public class Scheduler {

    IProgramacionService iProgramacionService;
    IProgramacionDetalleService iProgramacionDetalleService;
    ICitaService iCitaService;

    @Scheduled(cron = "${cron.expression}")
    public void scheduleTask() throws Exception {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        ZoneId systemTimeZone = ZoneId.systemDefault();
        Date fechaActual = new Date();
          System.out.println(" fechaActual " + fechaActual);

        String strFechaActual = simpleDateFormat.format(fechaActual);

        Set<Programacion> listaProgramacion = iProgramacionService.programacionEntityActivo();
//        fechaActual.before(programacion.getFechaInicial())// pendiente
        for(Programacion programacion : listaProgramacion) {
            if (!(fechaActual.after(programacion.getFechaInicial()) && fechaActual.before(programacion.getFechaFinal()))) {
//                System.out.println("p " + programacion) ;
                programacion.setActivo(Constants.INACTIVO);
              try {
                  iProgramacionService.modificarEntity(programacion);
              } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          } 
        }


        Set<ProgramacionDetalle> listaProgramacionDetalle = iProgramacionDetalleService.getProgramacionDetalleActivo(Constants.ACTIVO);
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
        
        Set<Cita> citas = iCitaService.listarNoAtendidos(idProgramacionDetalle, Constants.DESATENDIDO);
        
        for(Cita cita : citas) {
            if(null == cita.getCliente()) {
                cita.setEstado(3); //no programado
            }else {
                cita.setEstado(2);//no asistio
            }
//            System.out.println("valor cita "+ cita);
            iCitaService.modificarToEntity(cita);
        }
    }
    
    
}
