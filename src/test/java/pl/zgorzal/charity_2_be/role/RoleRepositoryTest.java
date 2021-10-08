package pl.zgorzal.charity_2_be.role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName_roleExists_resultRole() {
        String name = "ROLE_USER";
        Role addRole = new Role();
        addRole.setName(name);
        roleRepository.save(addRole);
        Role role = roleRepository.findByName(name).orElse(null);
        assertNotNull(role);
    }

    @Test
    void findByName_roleNotExist_resultNull() {
        String name = "notExist";
        Role role = roleRepository.findByName(name).orElse(null);
        assertNull(role);
    }

}