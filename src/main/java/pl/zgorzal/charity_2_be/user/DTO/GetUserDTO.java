package pl.zgorzal.charity_2_be.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserDTO {

    private Long id;
    private String email;
}
