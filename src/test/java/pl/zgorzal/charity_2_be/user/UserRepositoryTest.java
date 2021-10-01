package pl.zgorzal.charity_2_be.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_userExists_resultUser() {
        //given
        String email = "test@test.pl";
        // when
        User user = userRepository.findByEmail(email);
        // then
        assertNotNull(user);
    }

    @Test
    void findByEmail_userNotExist_resultNull() {
        //given
        String email = "notExist@test.pl";
        // when
        User user = userRepository.findByEmail(email);
        // then
        assertNull(user);
    }

}