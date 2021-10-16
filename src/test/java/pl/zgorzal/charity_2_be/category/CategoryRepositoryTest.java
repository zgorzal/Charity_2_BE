package pl.zgorzal.charity_2_be.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByName_categoryExists_resultCategory() {
        String name = "category name";
        Category addCategory = new Category();
        addCategory.setName(name);
        categoryRepository.save(addCategory);
        Category category = categoryRepository.findByName(name);
        assertNotNull(category);
    }

    @Test
    void findByName_categoryNotExist_resultNull() {
        String name = "Not exist";
        Category category = categoryRepository.findByName(name);
        assertNull(category);
    }
}