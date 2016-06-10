package com.happy.hipok.validation;


import com.happy.hipok.repository.AnatomicZoneRefRepository;
import com.happy.hipok.validation.annotations.ValidAnatomicZoneRefId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AnatomicZoneRefIdValidator implements ConstraintValidator<ValidAnatomicZoneRefId, Long> {

    @Autowired
    private AnatomicZoneRefRepository anatomicZoneRefRepository;

    public void initialize(ValidAnatomicZoneRefId constraintAnnotation) {
    }

    public boolean isValid(Long anatomicZoneId, ConstraintValidatorContext constraintContext) {

        if (anatomicZoneId == null) {
            return true;
        }

        return anatomicZoneRefRepository.exists(anatomicZoneId);
    }

}
