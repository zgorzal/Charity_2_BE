package pl.zgorzal.charity_2_be.validator;

import pl.zgorzal.charity_2_be.user.DTO.AddUserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CorrectRepeatPasswordValidator implements ConstraintValidator<CorrectRepeatPassword, AddUserDTO> {

    @Override
    public boolean isValid(AddUserDTO addUserDTO, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = addUserDTO.getPassword().equals(addUserDTO.getRepeatPassword());
        if (!isValid) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Podane hasła muszą być identyczne")
                    .addPropertyNode("repeatPassword")
                    .addConstraintViolation();
        }
        return isValid;
    }

}
