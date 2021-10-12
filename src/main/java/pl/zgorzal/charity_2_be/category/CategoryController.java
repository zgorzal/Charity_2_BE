package pl.zgorzal.charity_2_be.category;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void addCategory(@Valid @RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public void updateCategory(@Valid @RequestBody Category category) {
        try {
            categoryService.updateCategory(category);
        } catch (Exception e) {
            throw new AppRequestException("Podana kategoria już istnieje");
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
