package com.gafahtec.consultorio.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CitadosResponse {
    private String horario;
    private String paciente;
}
