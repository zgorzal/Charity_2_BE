package pl.zgorzal.charity_2_be.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AuthenticationResponse implements Serializable {
    private final String jwt;
}
