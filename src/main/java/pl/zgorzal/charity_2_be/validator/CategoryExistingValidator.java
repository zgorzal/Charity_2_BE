package pl.zgorzal.charity_2_be.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.zgorzal.charity_2_be.category.CategoryRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryExistingValidator implements ConstraintValidator<CategoryExisting, String> {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return categoryRepository.findByName(name) == null;
    }
}
