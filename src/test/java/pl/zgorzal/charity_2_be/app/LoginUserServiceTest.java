package pl.zgorzal.charity_2_be.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.zgorzal.charity_2_be.user.User;
import pl.zgorzal.charity_2_be.user.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginUserServiceTest {

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_userExists_returnUser() {
        String email = "test@test.pl";
        User user = new User(email, "password", true, new HashSet<>());
        userRepository.save(user);
        UserDetails userDetails = loginUserService.loadUserByUsername(email);
        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsername_userNotExist_throwUsernameNotFoundException() {
        String email = "notExist@test.pl";
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> loginUserService.loadUserByUsername(email));
    }
}