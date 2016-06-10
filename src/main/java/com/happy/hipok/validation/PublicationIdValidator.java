package com.happy.hipok.validation;


import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.validation.annotations.ValidPublicationId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PublicationIdValidator implements ConstraintValidator<ValidPublicationId, Long> {

    @Autowired
    private PublicationRepository publicationRepository;

    public void initialize(ValidPublicationId constraintAnnotation) {
    }

    public boolean isValid(Long publicationId, ConstraintValidatorContext constraintContext) {

        if (publicationId == null) {
            return true;
        }
        return publicationRepository.exists(publicationId);
    }

}
