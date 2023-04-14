package com.gafahtec.consultorio.validators;

import com.gafahtec.consultorio.annotations.UniqueEmail;
import com.gafahtec.consultorio.model.Usuario;
import com.gafahtec.consultorio.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {


    IUsuarioRepository iUsuarioRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Usuario usuario = iUsuarioRepository.findByEmail(value);
        if (usuario == null) {
            return true;
        }
        return false;
    }

}
