package pl.zgorzal.charity_2_be.donation;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zgorzal.charity_2_be.category.Category;
import pl.zgorzal.charity_2_be.institution.Institution;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = Donation.TABLE_NAME)
public class Donation {
    public static final String TABLE_NAME = "donations";

    public Donation(Integer quantity,
                    List<Category> categories,
                    Institution institution,
                    String street,
                    String city,
                    String zipCode,
                    LocalDate pickUpDate,
                    LocalTime pickUpTime,
                    String pickUpComment) {
        this.quantity = quantity;
        this.categories = categories;
        this.institution = institution;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.pickUpComment = pickUpComment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "donations_categories",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToOne
    private Institution institution;

    private String street;

    private String city;

    private String zipCode;

    private LocalDate pickUpDate;

    private LocalTime pickUpTime;

    private String pickUpComment;

}
