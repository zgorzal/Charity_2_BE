package pl.zgorzal.charity_2_be.user;

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
import pl.zgorzal.charity_2_be.role.Role;
import pl.zgorzal.charity_2_be.role.RoleRepository;
import pl.zgorzal.charity_2_be.user.DTO.GetUserDTO;

import javax.transaction.Transactional;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void addUser() throws Exception {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"test@test.pl\"," +
                        "\"password\": \"password\"," +
                        "\"repeatPassword\" : \"password\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        assertNotNull(userRepository.findByEmail("test@test.pl"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"test@test.pl\"," +
                        "\"password\": \"password\"," +
                        "\"repeatPassword\" : \"password\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"wrongEmail\"," +
                        "\"password\": \"password\"," +
                        "\"repeatPassword\" : \"password\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"test@test.pl\"," +
                        "\"password\": \"\"," +
                        "\"repeatPassword\" : \"\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"test@test.pl\"," +
                        "\"password\": \"password\"," +
                        "\"repeatPassword\" : \"wrongRepeatPassword\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"TEST\"," +
                        "\"password\": \"\"," +
                        "\"repeatPassword\" : \"wrongRepeatPassword\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    @WithMockUser
    void getUser_authorized() throws Exception {
        User user = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/" + user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        GetUserDTO getUserDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GetUserDTO.class);
        assertNotNull(getUserDTO);
        assertEquals(getUserDTO.getId(), user.getId());
        assertEquals(getUserDTO.getEmail(), "test@test.pl");

        mockMvc.perform(MockMvcRequestBuilders.get("/user/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void getUser_notAuthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser
    void updateUser_authorized() throws Exception {
        User user = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + user.getId() + "," +
                        "\"email\": \"new@email.pl\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        User newUser = userRepository.getById(user.getId());
        assertEquals(newUser.getEmail(), "new@email.pl");

        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + 0 + "," +
                        "\"email\": \"new@email.pl\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + user.getId() + "," +
                        "\"email\": \"wrong email\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));

        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + user.getId() + "," +
                        "\"email\": \"new@email.pl\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void updateUser_notAuthorized() throws Exception {
        User user = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .header("Content-Type", "application/json")
                .content("{\"id\":" + user.getId() + "," +
                        "\"email\": \"new@email.pl\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    @WithMockUser
    void deleteUser_authorized() throws Exception {
        User user = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));

        User checkUser = userRepository.findById(user.getId()).orElse(null);
        assertNull(checkUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void deleteUser_notAuthorized() throws Exception {
        User user = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

}