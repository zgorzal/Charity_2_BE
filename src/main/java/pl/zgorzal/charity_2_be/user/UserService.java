package pl.zgorzal.charity_2_be.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    //Add user

    //Find user
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    //Update user

    //Delete user

}
