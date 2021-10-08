package pl.zgorzal.charity_2_be.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.zgorzal.charity_2_be.app.AuthenticationRequest;
import pl.zgorzal.charity_2_be.app.AuthenticationResponse;
import pl.zgorzal.charity_2_be.app.Jwt;
import pl.zgorzal.charity_2_be.app.LoginUserService;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

@AllArgsConstructor
@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final Jwt jwt;
    private final LoginUserService loginUserService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AppRequestException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new AppRequestException("Incorrect user email or password", e);
        }

        final UserDetails userDetails = loginUserService.loadUserByUsername(
                authenticationRequest.getEmail());

        final String jwtToken = jwt.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @Secured("ROLE_USER")
    @GetMapping("/secured")
    public String secured() {
        return "secured";
    }
}
