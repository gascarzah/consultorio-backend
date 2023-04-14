package com.gafahtec.consultorio.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GenerarProgramacionFechas {


//    public static  void main(String arg[]){
//        try {
//            rangoProgramacion("22/08/2022", "27/08/2022");
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public static List<LocalDate> listaFechas(Date fechaInicial, Date fechaFinal) throws ParseException {
//        Date startDate = DateUtils.parseDate(fechaInicial, new String[] { "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy" });
//        Date endDate = DateUtils.parseDate(fechaFinal, new String[] { "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy" }) ;

        LocalDate startLocalDate = fechaInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = fechaFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return  getDatesRangeJava9(startLocalDate, endLocalDate);


    }

    public static List<LocalDate> getDatesRangeJava9(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1) ).collect(Collectors.toList());
    }
    
    

}
