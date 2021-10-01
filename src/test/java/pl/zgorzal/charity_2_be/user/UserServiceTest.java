package pl.zgorzal.charity_2_be.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getUser_userExists_resultUser() {
        //given
        String email = "test@test.pl";
        // when
        User user = userService.getUser(email);
        // then
        assertNotNull(user);
    }

    @Test
    void getUser_userNotExist_resultNull() {
        //given
        String email = "notExist@test.pl";
        // when
        User user = userService.getUser(email);
        // then
        assertNull(user);
    }

}