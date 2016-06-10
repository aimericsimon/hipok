package com.happy.hipok.validation;

import com.happy.hipok.repository.SituationRefRepository;
import com.happy.hipok.validation.annotations.ValidSituationRefId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class SituationRefIdValidator implements ConstraintValidator<ValidSituationRefId, Long> {

    @Autowired
    private SituationRefRepository situationRefRepository;

    public void initialize(ValidSituationRefId constraintAnnotation) {
    }

    public boolean isValid(Long situationRefId, ConstraintValidatorContext constraintContext) {

        if (situationRefId == null) {
            return true;
        }

        return situationRefRepository.exists(situationRefId);
    }

}
