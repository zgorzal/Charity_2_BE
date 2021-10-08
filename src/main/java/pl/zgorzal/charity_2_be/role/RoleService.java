package pl.zgorzal.charity_2_be.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new AppRequestException("Role not found"));
    }
}
