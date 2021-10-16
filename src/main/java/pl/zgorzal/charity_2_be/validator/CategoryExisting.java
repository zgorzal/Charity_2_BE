package pl.zgorzal.charity_2_be.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CategoryExistingValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryExisting {
    String message() default "{CategoryExisting.error.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
