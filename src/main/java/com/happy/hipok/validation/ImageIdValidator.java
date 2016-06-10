package com.happy.hipok.validation;

import com.happy.hipok.repository.ImageRepository;
import com.happy.hipok.validation.annotations.ValidImageId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ImageIdValidator implements ConstraintValidator<ValidImageId, Long> {

    @Autowired
    private ImageRepository imageRepository;

    public void initialize(ValidImageId constraintAnnotation) {
    }

    public boolean isValid(Long imageId, ConstraintValidatorContext constraintContext) {

        if (imageId == null) {
            return true;
        }
        return imageRepository.exists(imageId);
    }

}
