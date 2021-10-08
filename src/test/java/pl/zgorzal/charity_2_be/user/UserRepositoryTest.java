package pl.zgorzal.charity_2_be.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_userExists_resultUser() {
        String email = "test@test.pl";
        User addUser = new User(email, "password", true, new HashSet<>());
        userRepository.save(addUser);
        User user = userRepository.findByEmail(email);
        assertNotNull(user);
    }

    @Test
    void findByEmail_userNotExist_resultNull() {
        String email = "notExist@test.pl";
        User user = userRepository.findByEmail(email);
        assertNull(user);
    }

}