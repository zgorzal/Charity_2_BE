package pl.zgorzal.charity_2_be.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void addCategory_authorized() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"category name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        assertNotNull(categoryRepository.findByName("category name"));

        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"category name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                .header("Content-Type", "application/json")
                .content("{\"name\": \" \"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @WithMockUser()
    void addCategory_notAuthorized_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"category name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void addCategory_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"category name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser
    void getCategory_authorized() throws Exception {
        Category category = new Category();
        category.setName("test");
        categoryRepository.save(category);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/category/" + category.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Category checkCategory = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);
        assertNotNull(checkCategory);
        assertEquals(checkCategory.getId(), category.getId());
        assertEquals(checkCategory.getName(), "test");

        mockMvc.perform(MockMvcRequestBuilders.get("/category/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void getCategory_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateCategory_authorized() throws Exception {
        Category category = new Category();
        category.setName("category name");
        categoryRepository.save(category);

        mockMvc.perform(MockMvcRequestBuilders.put("/category")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + category.getId() + "," +
                        "\"name\": \"new name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        Category newCategory = categoryRepository.getById(category.getId());
        assertEquals(newCategory.getName(), "new name");

        mockMvc.perform(MockMvcRequestBuilders.put("/category")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + 0 + "," +
                        "\"name\": \"new name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/category")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + category.getId() + "," +
                        "\"name\": \" \"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    @WithMockUser
    void updateCategory_notAuthorized_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/category")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + 1 + "," +
                        "\"name\": \"new name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void updateCategory_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/category")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + 1 + "," +
                        "\"name\": \"new name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteCategory_authorized() throws Exception {
        Category category = new Category();
        category.setName("category name");
        categoryRepository.save(category);

        mockMvc.perform(MockMvcRequestBuilders.delete("/category/" + category.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        Category checkCategory = categoryRepository.findById(category.getId()).orElse(null);
        assertNull(checkCategory);

        mockMvc.perform(MockMvcRequestBuilders.delete("/category/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    @Test
    @WithMockUser
    void deleteCategory_notAuthorized_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void deleteCategory_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }
}