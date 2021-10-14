package pl.zgorzal.charity_2_be.institution;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = Institution.TABLE_NAME)
public class Institution {
    public static final String TABLE_NAME = "institutions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa nie może być pusta")
    @Column(unique = true)
    private String name;

    private String description;
}
