package pl.zgorzal.charity_2_be.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.zgorzal.charity_2_be.user.DTO.AddUserDTO;
import pl.zgorzal.charity_2_be.user.DTO.GetUserDTO;
import pl.zgorzal.charity_2_be.user.DTO.UpdateUserDTO;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void addUser(@Valid @RequestBody AddUserDTO addUserDTO) {
        userService.addUser(addUserDTO);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public GetUserDTO getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return new GetUserDTO(user.getId(), user.getEmail());
    }

    @Secured("ROLE_USER")
    @PutMapping
    public void updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUser(updateUserDTO);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
