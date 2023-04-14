package com.gafahtec.consultorio.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter

@Getter

@AllArgsConstructor

@NoArgsConstructor

@ToString
public class UsuarioLoginRequest {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

}


