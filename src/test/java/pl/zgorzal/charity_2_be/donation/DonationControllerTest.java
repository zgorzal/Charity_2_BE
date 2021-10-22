package pl.zgorzal.charity_2_be.donation;

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
import pl.zgorzal.charity_2_be.category.Category;
import pl.zgorzal.charity_2_be.category.CategoryRepository;
import pl.zgorzal.charity_2_be.institution.Institution;
import pl.zgorzal.charity_2_be.institution.InstitutionRepository;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    @WithMockUser()
    void addDonation_authorized() throws Exception {
        Category category = new Category();
        category.setName("Category test");
        categoryRepository.save(category);

        Institution institution = new Institution();
        institution.setName("Institution test");
        institution.setDescription("Institution description test");
        institutionRepository.save(institution);

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        assertEquals(donationRepository.findAll().stream()
                .filter(x -> x.getPickUpComment().equals("Comment test")).count(), 1);

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"0 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": []," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": \"\"," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"2020-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": \"1 \"," +
                        " \"categoriesId\": [" + category.getId() + "]," +
                        " \"institutionId\": " + institution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    void addDonation_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/donation")
                .header("Content-Type", "application/json")
                .content("{\"quantity\": 1," +
                        " \"categoriesId\": [1]," +
                        " \"institutionId\": 1," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"2021-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser()
    void getDonation_authorized() throws Exception {
        Category category = new Category();
        category.setName("Category test");
        categoryRepository.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Institution institution = new Institution();
        institution.setName("Institution test");
        institution.setDescription("Institution description test");
        institutionRepository.save(institution);

        Donation donation = new Donation(
                1,
                categories,
                institution,
                "street test",
                "city test",
                "00-000",
                LocalDate.now().plusDays(1),
                LocalTime.of(11, 30),
                "comment test"
        );

        donationRepository.save(donation);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/donation/" + donation.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();


        Donation checkDonation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Donation.class);
        assertNotNull(checkDonation);
        assertEquals(checkDonation.getId(), donation.getId());
        assertEquals(checkDonation.getCategories(), donation.getCategories());
        assertEquals(checkDonation.getInstitution(), donation.getInstitution());
        assertEquals(checkDonation.getStreet(), donation.getStreet());
        assertEquals(checkDonation.getCity(), donation.getCity());
        assertEquals(checkDonation.getZipCode(), donation.getZipCode());
        assertEquals(checkDonation.getPickUpDate(), donation.getPickUpDate());
        assertEquals(checkDonation.getPickUpTime(), donation.getPickUpTime());
        assertEquals(checkDonation.getPickUpComment(), donation.getPickUpComment());

        mockMvc.perform(MockMvcRequestBuilders.get("/donation/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void getDonation_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/donation/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser()
    void updateDonation_authorized() throws Exception {
        Category category = new Category();
        category.setName("Category test");
        categoryRepository.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Institution institution = new Institution();
        institution.setName("Institution test");
        institution.setDescription("Institution description test");
        institutionRepository.save(institution);

        Donation donation = new Donation(
                1,
                categories,
                institution,
                "street test",
                "city test",
                "00-000",
                LocalDate.now().plusDays(1),
                LocalTime.of(11, 30),
                "comment test"
        );

        donationRepository.save(donation);

        Category newCategory = new Category();
        newCategory.setName("new category test");
        categoryRepository.save(newCategory);


        Institution newInstitution = new Institution();
        newInstitution.setName("new institution test");
        newInstitution.setDescription("new institution description test");
        institutionRepository.save(newInstitution);

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"new street test\"," +
                        " \"city\": \"new city test\"," +
                        " \"zipCode\": \"99-999\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"12:00\"," +
                        " \"pickUpComment\":\"new comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        Donation newDonation = donationRepository.getById(donation.getId());
        assertNotNull(newDonation);
        assertEquals(newDonation.getId(), donation.getId());
        assertEquals(newDonation.getCategories().get(0), newCategory);
        assertEquals(newDonation.getInstitution(), newInstitution);
        assertEquals(newDonation.getStreet(), "new street test");
        assertEquals(newDonation.getCity(), "new city test");
        assertEquals(newDonation.getZipCode(), "99-999");
        assertEquals(newDonation.getPickUpDate(), LocalDate.of(3000, 12, 12));
        assertEquals(newDonation.getPickUpTime(), LocalTime.of(12, 0));
        assertEquals(newDonation.getPickUpComment(), "new comment test");


        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\": 0," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 0," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": []," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": \"\"," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"2020-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + donation.getId() + "," +
                        "\"quantity\": 2," +
                        " \"categoriesId\": [" + newCategory.getId() + "]," +
                        " \"institutionId\": " + newInstitution.getId() + "," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"3000-12-12\"," +
                        " \"pickUpTime\": \"\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void updateDonation_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/donation")
                .header("Content-Type", "application/json")
                .content("{\"id\": 1," +
                        "\"quantity\": 1," +
                        " \"categoriesId\": [1]," +
                        " \"institutionId\": 1," +
                        " \"street\": \"street test\"," +
                        " \"city\": \"city test\"," +
                        " \"zipCode\": \"09-090\"," +
                        " \"pickUpDate\": \"2021-12-12\"," +
                        " \"pickUpTime\": \"11:00\"," +
                        " \"pickUpComment\":\"Comment test\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteDonation_authorized() throws Exception {
        Category category = new Category();
        category.setName("Category test");
        categoryRepository.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Institution institution = new Institution();
        institution.setName("Institution test");
        institution.setDescription("Institution description test");
        institutionRepository.save(institution);

        Donation donation = new Donation(
                1,
                categories,
                institution,
                "street test",
                "city test",
                "00-000",
                LocalDate.now().plusDays(1),
                LocalTime.of(11, 30),
                "comment test"
        );

        donationRepository.save(donation);

        mockMvc.perform(MockMvcRequestBuilders.delete("/donation/" + donation.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        Donation checkDonation = donationRepository.findById(donation.getId()).orElse(null);
        assertNull(checkDonation);

        mockMvc.perform(MockMvcRequestBuilders.delete("/donation/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    @WithMockUser
    void deleteDonation_notAuthorized_User() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/donation/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    void deleteDonation_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/donation/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }
}