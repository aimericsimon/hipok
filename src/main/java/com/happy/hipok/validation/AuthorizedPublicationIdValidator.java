package com.happy.hipok.validation;


import com.happy.hipok.domain.Publication;
import com.happy.hipok.repository.PublicationRepository;
import com.happy.hipok.validation.annotations.AuthorizedPublicationId;
import com.happy.hipok.validation.annotations.ValidPublicationId;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AuthorizedPublicationIdValidator implements ConstraintValidator<AuthorizedPublicationId, Long> {

    @Autowired
    private PublicationRepository publicationRepository;

    public void initialize(AuthorizedPublicationId constraintAnnotation) {
    }

    public boolean isValid(Long publicationId, ConstraintValidatorContext constraintContext) {

        if (publicationId == null) {
            return true;
        }

        Publication publicationWithCurrentProfile = publicationRepository.getPublicationWithCurrentProfile(publicationId);
        if (publicationWithCurrentProfile == null) {
            return false;
        }
        return true;
    }

}
