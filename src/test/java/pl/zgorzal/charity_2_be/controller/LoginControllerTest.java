package pl.zgorzal.charity_2_be.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zgorzal.charity_2_be.role.Role;
import pl.zgorzal.charity_2_be.role.RoleRepository;
import pl.zgorzal.charity_2_be.user.User;
import pl.zgorzal.charity_2_be.user.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void createAuthenticationToken() throws Exception {
        String email = "test@test.pl";
        String password = "password";

        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(password);

        User user = new User(email, encodePassword, true, new HashSet<>(Collections.singletonList(role)));
        userRepository.save(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"test@test.pl\"," +
                        "\"password\": \"password\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        String token = JsonPath.parse(response).read("$.jwt");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"test@test.pl\"," +
                        "\"password\": \"wrong password\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .header("Content-Type", "application/json")
                .content("{\"email\": \"wrongEmail@test.pl\"," +
                        "\"password\": \"password\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));

        mockMvc.perform(MockMvcRequestBuilders.get("/secured")
                .header("Authorization", token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("secured"));

        mockMvc.perform(MockMvcRequestBuilders.get("/secured"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));

        String wrongToken = "Wrong token";

        mockMvc.perform(MockMvcRequestBuilders.get("/secured")
                .header("Authorization", wrongToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

}