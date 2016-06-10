package com.happy.hipok.validation;


import com.happy.hipok.repository.SpecialtyRefRepository;
import com.happy.hipok.validation.annotations.ValidSpecialtyRefId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class SpecialtyRefIdValidator implements ConstraintValidator<ValidSpecialtyRefId, Long> {

    @Autowired
    private SpecialtyRefRepository specialtyRefRepository;

    public void initialize(ValidSpecialtyRefId constraintAnnotation) {
    }

    public boolean isValid(Long specialtyRefId, ConstraintValidatorContext constraintContext) {

        if (specialtyRefId == null) {
            return true;
        }

        return specialtyRefRepository.exists(specialtyRefId);
    }

}
