package pl.zgorzal.charity_2_be.institution;

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
class InstitutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void addInstitution_authorized() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/institution")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"institution name\"," +
                        "\"description\":\"description text\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        assertNotNull(institutionRepository.findByName("institution name"));

        mockMvc.perform(MockMvcRequestBuilders.post("/institution")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"institution name\"," +
                        "\"description\":\"description text\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/institution")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"\"," +
                        "\"description\":\"description text\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @WithMockUser()
    void addInstitution_notAuthorized_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/institution")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"institution name\"," +
                        "\"description\":\"description text\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void addInstitution_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/institution")
                .header("Content-Type", "application/json")
                .content("{\"name\": \"institution name\"," +
                        "\"description\":\"description text\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser
    void getInstitution_authorized() throws Exception {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionRepository.save(institution);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/institution/" + institution.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Institution checkInstitution = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Institution.class);
        assertNotNull(checkInstitution);
        assertEquals(checkInstitution.getId(), institution.getId());
        assertEquals(checkInstitution.getName(), "test");
        assertEquals(checkInstitution.getDescription(), "test test");

        mockMvc.perform(MockMvcRequestBuilders.get("/institution/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void getInstitution_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/institution/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateInstitution_authorized() throws Exception {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionRepository.save(institution);

        mockMvc.perform(MockMvcRequestBuilders.put("/institution")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + institution.getId() + "," +
                        "\"name\": \"new name\", " +
                        "\"description\":\"new description\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        Institution newInstitution = institutionRepository.getById(institution.getId());
        assertEquals(newInstitution.getName(), "new name");
        assertEquals(newInstitution.getDescription(), "new description");

        mockMvc.perform(MockMvcRequestBuilders.put("/institution")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + 0 + "," +
                        "\"name\": \"new name\", " +
                        "\"description\":\"new description\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/institution")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + institution.getId() + "," +
                        "\"name\": \"\", " +
                        "\"description\":\"new description\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    @WithMockUser
    void updateInstitution_notAuthorized_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/institution")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + 1 + "," +
                        "\"name\": \"new name\", " +
                        "\"description\":\"new description\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void updateInstitution_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/institution")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + 1 + "," +
                        "\"name\": \"new name\", " +
                        "\"description\":\"new description\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteInstitution_authorized() throws Exception {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionRepository.save(institution);

        mockMvc.perform(MockMvcRequestBuilders.delete("/institution/" + institution.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        Institution checkInstitution = institutionRepository.findById(institution.getId()).orElse(null);
        assertNull(checkInstitution);

        mockMvc.perform(MockMvcRequestBuilders.delete("/institution/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    @Test
    @WithMockUser
    void deleteInstitution_notAuthorized_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/institution/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void deleteInstitution_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/institution/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

}