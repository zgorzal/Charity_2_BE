package pl.zgorzal.charity_2_be.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppRequestException("Category not found"));
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public void updateCategory(Category category) {
        Category updateCategory = getCategory(category.getId());
        updateCategory.setName(category.getName());
        categoryRepository.save(updateCategory);
    }

    public void deleteCategory(Long id) {
        Category category = getCategory(id);
        categoryRepository.delete(category);
    }
}
