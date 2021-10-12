package pl.zgorzal.charity_2_be.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void addCategory_correctCategory_resultCategory() {
        Category category = new Category();
        category.setName("test");
        categoryService.addCategory(category);
        assertNotNull(categoryService.getCategory(category.getId()));
    }

    @Test
    void addCategory_categoryExists_throwException() {
        Category category = new Category();
        category.setName("test");
        categoryService.addCategory(category);

        Category newCategory = new Category();
        newCategory.setName("test");

        Assertions.assertThrows(Exception.class,
                () -> categoryService.addCategory(newCategory));
    }

    @Test
    void getCategory_categoryExists_resultCategory() {
        Category category = new Category();
        category.setName("test");
        categoryService.addCategory(category);

        Category findCategory = categoryService.getCategory(category.getId());
        assertNotNull(findCategory);
    }

    @Test
    void getCategory_categoryNotExists_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> categoryService.getCategory(1L));
    }

    @Test
    void updateCategory_categoryExists_resultNewCategoryName() {
        Category category = new Category();
        category.setName("test");
        categoryService.addCategory(category);

        String newName = "new name";
        category.setName(newName);
        categoryService.updateCategory(category);

        assertEquals(categoryService.getCategory(category.getId()).getName(), newName);
    }

    @Test
    void updateCategory_categoryNotExists_throwAppRequestException() {
        Category category = new Category();
        category.setId(1L);
        category.setName("test");

        Assertions.assertThrows(AppRequestException.class,
                () -> categoryService.updateCategory(category));
    }

    @Test
    void deleteCategory_categoryExists_resultNull() {
        Category category = new Category();
        category.setName("test");
        categoryService.addCategory(category);

        categoryService.deleteCategory(category.getId());

        assertNull(categoryRepository.findById(category.getId()).orElse(null));
    }

    @Test
    void deleteCategory_categoryNotExists_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> categoryService.deleteCategory(1L));
    }
}