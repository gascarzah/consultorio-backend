package com.gafahtec.consultorio.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gafahtec.consultorio.model.consultorio.Horario;

import java.util.List;

@Repository
public interface IHorarioRepository extends IGenericRepository<Horario,Integer>{


    List<Horario> findByIdEmpresa(Integer idEmpresa);

    @Query("""
    SELECT h 
    FROM Horario h 
    WHERE h.idHorario NOT IN (
                                SELECT c.horario.idHorario 
                                FROM Cita c 
                                WHERE c.horario IS NOT NULL
                                AND c.programacionDetalle.idProgramacionDetalle = :idProgramacionDetalle
    )
    AND h.idEmpresa = :idEmpresa
""")
    List<Horario> obtenerHorariosDisponibles(@Param("idProgramacionDetalle") Integer idProgramacionDetalle,@Param("idEmpresa") Integer idEmpresa);
}
