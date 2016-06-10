package com.happy.hipok.validation.annotations;

import com.happy.hipok.validation.RPPSAndAdeliNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = RPPSAndAdeliNumberValidator.class)
@Documented
public @interface ValidRPPSAndAdeliNumber {

    String message() default "{com.happy.hipok.validation.rppsandadelinumber}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
