package pl.zgorzal.charity_2_be.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.zgorzal.charity_2_be.institution.InstitutionRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InstitutionExistingValidator implements ConstraintValidator<InstitutionExisting, String> {
    @Autowired
    private InstitutionRepository institutionRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return institutionRepository.findByName(name) == null;
    }
}
