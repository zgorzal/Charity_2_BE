package pl.zgorzal.charity_2_be.category;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping()
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public void updateCategory(@Valid @RequestBody Category category) {
        categoryService.updateCategory(category);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
