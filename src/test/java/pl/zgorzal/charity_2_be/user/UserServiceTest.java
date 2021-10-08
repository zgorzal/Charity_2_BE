package pl.zgorzal.charity_2_be.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zgorzal.charity_2_be.exception.AppRequestException;
import pl.zgorzal.charity_2_be.role.Role;
import pl.zgorzal.charity_2_be.role.RoleRepository;
import pl.zgorzal.charity_2_be.user.DTO.AddUserDTO;
import pl.zgorzal.charity_2_be.user.DTO.UpdateUserDTO;

import javax.transaction.Transactional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void addUser_correctUser_resultUser() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        String email = "test@test.pl";
        AddUserDTO addUserDTO = new AddUserDTO(email, "password", "password");

        userService.addUser(addUserDTO);
        assertNotNull(userService.getUser(email));
    }

    @Test
    void addUser_emailExists_throwException() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        String email = "test@test.pl";
        AddUserDTO addUserDTO = new AddUserDTO(email, "password", "password");
        userService.addUser(addUserDTO);

        Assertions.assertThrows(Exception.class,
                () -> userService.addUser(addUserDTO));
    }

    @Test
    void getUser_userExists_resultUser() {
        String email = "test@test.pl";
        User addUser = new User(email, "password", true, new HashSet<>());
        userRepository.save(addUser);

        User user = userService.getUser(email);

        assertNotNull(user);
    }

    @Test
    void getUser_userNotExist_resultNull() {
        String email = "notExist@test.pl";
        User user = userService.getUser(email);
        assertNull(user);
    }

    @Test
    void getUser_userIDExists_resultUser() {
        User addUser = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(addUser);

        User user = userService.getUser(addUser.getId());

        assertNotNull(user);
    }

    @Test
    void getUser_userIDNotExist_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> userService.getUser(1L));
    }

    @Test
    void updateUser_userExists_resultNewUserEmail() {
        User user = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(user);

        String newEmail = "new@email.pl";
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(user.getId(), newEmail);

        userService.updateUser(updateUserDTO);

        assertEquals(userService.getUser(user.getId()).getEmail(), newEmail);
    }

    @Test
    void updateUser_userNotExists_throwAppRequestException() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(1L, "new@email.pl");

        Assertions.assertThrows(AppRequestException.class,
                () -> userService.updateUser(updateUserDTO));
    }

    @Test
    void deleteUser_userExists_resultNull() {
        User user = new User("test@test.pl", "password", true, new HashSet<>());
        userRepository.save(user);

        userService.deleteUser(user.getId());

        assertNull(userRepository.findById(user.getId()).orElse(null));
    }

    @Test
    void deleteUser_userNotExists_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> userService.deleteUser(1L));
    }

}