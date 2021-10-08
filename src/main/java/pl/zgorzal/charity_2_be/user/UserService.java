package pl.zgorzal.charity_2_be.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zgorzal.charity_2_be.exception.AppRequestException;
import pl.zgorzal.charity_2_be.role.Role;
import pl.zgorzal.charity_2_be.role.RoleService;
import pl.zgorzal.charity_2_be.user.DTO.AddUserDTO;
import pl.zgorzal.charity_2_be.user.DTO.UpdateUserDTO;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public void addUser(AddUserDTO addUserDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Role userRole = roleService.getRole("ROLE_USER");
        User user = new User(
                addUserDTO.getEmail(),
                passwordEncoder.encode(addUserDTO.getPassword()),
                true,
                new HashSet<>(Collections.singletonList(userRole))
        );
        userRepository.save(user);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new AppRequestException("User not found"));
    }

    public void updateUser(UpdateUserDTO updateUserDTO) {
        User user = getUser(updateUserDTO.getId());
        user.setEmail(updateUserDTO.getEmail());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUser(id);
        user.setRoles(new HashSet<>());
        userRepository.delete(user);
    }

}
