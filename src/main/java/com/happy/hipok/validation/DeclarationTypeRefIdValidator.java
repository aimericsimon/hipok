package com.happy.hipok.validation;

import com.happy.hipok.repository.DeclarationTypeRefRepository;
import com.happy.hipok.validation.annotations.ValidDeclarationTypeRefId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Dahwoud on 16/02/2016.
 */
@Component
public class DeclarationTypeRefIdValidator implements ConstraintValidator<ValidDeclarationTypeRefId, Long> {

    @Autowired
    private DeclarationTypeRefRepository declarationTypeRefRepository;

    public void initialize(ValidDeclarationTypeRefId constraintAnnotation) {
    }

    public boolean isValid(Long declarationTypeRefId, ConstraintValidatorContext constraintContext) {

        if (declarationTypeRefId == null) {
            return true;
        }

        return declarationTypeRefRepository.exists(declarationTypeRefId);
    }

}
