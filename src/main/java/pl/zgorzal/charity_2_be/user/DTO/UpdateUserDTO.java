package pl.zgorzal.charity_2_be.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.zgorzal.charity_2_be.validator.EmailExisting;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UpdateUserDTO {

    private Long id;

    @NotBlank
    @Email(message = "Podaj poprawny email")
    @EmailExisting(message = "Podany email już istnieje")
    private String email;
}
