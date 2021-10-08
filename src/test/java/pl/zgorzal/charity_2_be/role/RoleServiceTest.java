package pl.zgorzal.charity_2_be.role;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName_roleExists_resultRole() {
        String name = "ROLE_USER";
        Role addRole = new Role();
        addRole.setName(name);
        roleRepository.save(addRole);
        Role role = roleService.getRole(name);
        assertNotNull(role);
    }

    @Test
    void findByName_roleNotExist_throwAppRequestException() {
        String name = "notExist";
        Assertions.assertThrows(AppRequestException.class,
                () -> roleService.getRole(name));
    }
}