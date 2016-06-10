package com.happy.hipok.validation.annotations;

import com.happy.hipok.validation.TitleRefIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = TitleRefIdValidator.class)
@Documented
public @interface ValidTitleRefId {

    String message() default "{com.happy.hipok.validation.titlerefid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
