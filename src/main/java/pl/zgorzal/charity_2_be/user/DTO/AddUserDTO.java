package pl.zgorzal.charity_2_be.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.zgorzal.charity_2_be.validator.CorrectRepeatPassword;
import pl.zgorzal.charity_2_be.validator.EmailExisting;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@CorrectRepeatPassword
@Data
@AllArgsConstructor
public class AddUserDTO {

    @NotBlank
    @Email(message = "Podaj poprawny email")
    @EmailExisting(message = "Podany email już istnieje")
    private String email;

    @NotBlank(message = "Hasło nie może być puste")
    private String password;

    @NotBlank(message = "Powtórzone hasło nie może być puste")
    private String repeatPassword;

}
