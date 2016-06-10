package com.happy.hipok.validation;

import com.happy.hipok.repository.MedicalTypeRefRepository;
import com.happy.hipok.validation.annotations.ValidMedicalTypeRefId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MedicalTypeRefIdValidator implements ConstraintValidator<ValidMedicalTypeRefId, Long> {

    @Autowired
    private MedicalTypeRefRepository medicalTypeRefRepository;

    public void initialize(ValidMedicalTypeRefId constraintAnnotation) {
    }

    public boolean isValid(Long medicalTypeRefId, ConstraintValidatorContext constraintContext) {

        if (medicalTypeRefId == null) {
            return true;
        }

        return medicalTypeRefRepository.exists(medicalTypeRefId);
    }

}
