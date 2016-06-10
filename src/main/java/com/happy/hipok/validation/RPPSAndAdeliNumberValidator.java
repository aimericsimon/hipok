package com.happy.hipok.validation;

import com.happy.hipok.domain.SituationRef;
import com.happy.hipok.repository.SituationRefRepository;
import com.happy.hipok.validation.annotations.ValidRPPSAndAdeliNumber;
import com.happy.hipok.web.rest.app.dto.AppExtendedUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RPPSAndAdeliNumberValidator implements ConstraintValidator<ValidRPPSAndAdeliNumber, AppExtendedUserDTO> {

    @Autowired
    private SituationRefRepository situationRefRepository;

    public void initialize(ValidRPPSAndAdeliNumber constraintAnnotation) {
    }

    public boolean isValid(AppExtendedUserDTO appExtendedUserDTO, ConstraintValidatorContext constraintContext) {

        SituationRef situationRef = getSituationRef(appExtendedUserDTO);

        String adeliNumber = appExtendedUserDTO.getAdeliNumber();
        boolean hasAdeliNumber = adeliNumber != null && !adeliNumber.isEmpty();

        // Pour les étudiants seulement, Adeli n'est pas obligatoire.
        if (!isStudent(situationRef)) {
            return hasAdeliNumber;
        }

        return true;
    }

    private SituationRef getSituationRef(AppExtendedUserDTO appExtendedUserDTO) {
        SituationRef situationRef = null;
        Long situationRefId = appExtendedUserDTO.getSituationRefId();
        if (situationRefId != null) {
            situationRef = situationRefRepository.findOne(situationRefId);
        }
        return situationRef;
    }

    private boolean isStudent(SituationRef situationRef) {
        if (situationRef == null) {
            return false;
        }
        return "E".equals(situationRef.getCode());
    }
}
