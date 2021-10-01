package pl.zgorzal.charity_2_be.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pl.zgorzal.charity_2_be.app.AuthenticationRequest;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LoginControllerTest {

    @Autowired
    private LoginController loginController;

    @Test
    void createAuthenticationToken_correctLogin_returnJwtToken() {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("test@test.pl");
        authenticationRequest.setPassword("aa");
        // when
        ResponseEntity<?> jwtToken = loginController.createAuthenticationToken(authenticationRequest);
        // then
        assertNotNull(jwtToken);
    }

    @Test
    void createAuthenticationToken_incorrectEmail_throwAppRequestException() {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("incorrectEmail@test.pl");
        authenticationRequest.setPassword("aa");
        // when

        // then
        Assertions.assertThrows(AppRequestException.class,
                () -> loginController.createAuthenticationToken(authenticationRequest));
    }

    @Test
    void createAuthenticationToken_incorrectPassword_throwAppRequestException() {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("test@test.pl");
        authenticationRequest.setPassword("incorrect password");
        // when

        // then
        Assertions.assertThrows(AppRequestException.class,
                () -> loginController.createAuthenticationToken(authenticationRequest));
    }

}