package com.gafahtec.validators;

import com.gafahtec.annotations.UniqueEmail;
import com.gafahtec.repository.IUsuarioRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {


	IUsuarioRepository iUsuarioRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var usuario = iUsuarioRepository.findByEmail(value);
        if (usuario == null) {
            return true;
        }
        return false;
    }

}
