package pl.zgorzal.charity_2_be.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void addCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new AppRequestException("Podana kategoria już istnieje");
        }
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppRequestException("Category not found"));
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
