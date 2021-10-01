package pl.zgorzal.charity_2_be.app;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class LoginUser extends User {
    private final pl.zgorzal.charity_2_be.user.User user;

    public LoginUser(String email,
                     String password,
                     Collection<? extends GrantedAuthority> authorities,
                     pl.zgorzal.charity_2_be.user.User user) {
        super(email, password, authorities);
        this.user = user;
    }

}
