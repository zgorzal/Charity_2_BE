package pl.zgorzal.charity_2_be.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginUserServiceTest {

    @Autowired
    private LoginUserService loginUserService;

    @Test
    void loadUserByUsername_userExists_returnUser() {
        //given
        String email = "test@test.pl";
        //when
        UserDetails userDetails = loginUserService.loadUserByUsername(email);
        //then
        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsername_userNotExist_throwUsernameNotFoundException() {
        //given
        String email = "notExist@test.pl";
        //when

        //then
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> loginUserService.loadUserByUsername(email));
    }
}