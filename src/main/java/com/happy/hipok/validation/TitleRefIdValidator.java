package com.happy.hipok.validation;

import com.happy.hipok.repository.TitleRefRepository;
import com.happy.hipok.validation.annotations.ValidTitleRefId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class TitleRefIdValidator implements ConstraintValidator<ValidTitleRefId, Long> {

    @Autowired
    private TitleRefRepository titleRefRepository;

    public void initialize(ValidTitleRefId constraintAnnotation) {
    }

    public boolean isValid(Long titleRefId, ConstraintValidatorContext constraintContext) {

        if (titleRefId == null) {
            return true;
        }

        return titleRefRepository.exists(titleRefId);
    }

}
