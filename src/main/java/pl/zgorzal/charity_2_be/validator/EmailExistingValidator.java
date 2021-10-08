package pl.zgorzal.charity_2_be.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.zgorzal.charity_2_be.user.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistingValidator implements ConstraintValidator<EmailExisting, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userService.getUser(email) == null;
    }
}
