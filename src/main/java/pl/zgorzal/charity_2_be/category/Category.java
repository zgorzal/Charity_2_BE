package pl.zgorzal.charity_2_be.category;

import lombok.Data;
import pl.zgorzal.charity_2_be.validator.CategoryExisting;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = Category.TABLE_NAME)
public class Category {
    public static final String TABLE_NAME = "categories";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CategoryExisting(message = "Podana kategoria już istnieje")
    @NotBlank(message = "Nazwa nie może być pusta")
    @Column(unique = true)
    private String name;
}
