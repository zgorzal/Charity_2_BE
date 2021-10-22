package pl.zgorzal.charity_2_be.donation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AddDonationDTO {

    @Min(value = 1L, message = "Liczba worków musi być min. 1")
    private Integer quantity;

    @NotEmpty(message = "Musisz wybrać minimum jedną kategorię")
    private List<Long> categoriesId;

    @NotNull(message = "Musisz podać instytucje")
    private Long institutionId;

    @NotBlank(message = "Wartość ulica nie może być puste")
    private String street;

    @NotBlank(message = "Wartość miasto nie może być puste")
    private String city;

    @NotBlank(message = "Wartość kod pocztowy nie może być puste")
    private String zipCode;

    @NotNull(message = "Data nie może być pusta")
    @Future(message = "podany czas musi być przyszły")
    private LocalDate pickUpDate;

    @NotNull(message = "Czas nie może być pusty")
    private LocalTime pickUpTime;

    private String pickUpComment;
}
