package pl.zgorzal.charity_2_be.donation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zgorzal.charity_2_be.category.Category;
import pl.zgorzal.charity_2_be.category.CategoryRepository;
import pl.zgorzal.charity_2_be.donation.DTO.AddDonationDTO;
import pl.zgorzal.charity_2_be.donation.DTO.UpdateDonationDTO;
import pl.zgorzal.charity_2_be.exception.AppRequestException;
import pl.zgorzal.charity_2_be.institution.Institution;
import pl.zgorzal.charity_2_be.institution.InstitutionRepository;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DonationServiceTest {

    @Autowired
    private DonationService donationService;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    void addDonation_correctDonation_resultDonation() {
        Category category = new Category();
        category.setName("Test category");
        categoryRepository.save(category);

        List<Long> categoryId = new ArrayList<>();
        categoryId.add(category.getId());

        Institution institution = new Institution();
        institution.setName("Test institution");
        institutionRepository.save(institution);

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.now();

        AddDonationDTO addDonationDTO = new AddDonationDTO(
                1,
                categoryId,
                institution.getId(),
                "Street test",
                "city test",
                "zipCode test",
                date,
                time,
                "comment test");

        donationService.addDonation(addDonationDTO);
        assertEquals(donationRepository.findAll().stream()
                .filter(x -> x.getPickUpComment().equals("comment test")).count(), 1);
    }

    @Test
    void addDonation_categoryNotExists_throwAppRequestException() {
        List<Long> categoryId = new ArrayList<>();
        categoryId.add(1L);

        Institution institution = new Institution();
        institution.setName("Test institution");
        institutionRepository.save(institution);

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.now();

        AddDonationDTO addDonationDTO = new AddDonationDTO(
                1,
                categoryId,
                institution.getId(),
                "Street test",
                "city test",
                "zipCode test",
                date,
                time,
                "comment test");

        Assertions.assertThrows(AppRequestException.class,
                () -> donationService.addDonation(addDonationDTO));
    }

    @Test
    void addDonation_institutionNotExists_throwAppRequestException() {
        Category category = new Category();
        category.setName("Test category");
        categoryRepository.save(category);

        List<Long> categoryId = new ArrayList<>();
        categoryId.add(category.getId());

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.now();

        AddDonationDTO addDonationDTO = new AddDonationDTO(
                1,
                categoryId,
                1L,
                "Street test",
                "city test",
                "zipCode test",
                date,
                time,
                "comment test");

        Assertions.assertThrows(AppRequestException.class,
                () -> donationService.addDonation(addDonationDTO));
    }

    @Test
    void getDonation_donationExists_resultDonation() {
        Category category = new Category();
        category.setName("Test category");
        categoryRepository.save(category);

        List<Long> categoryId = new ArrayList<>();
        categoryId.add(category.getId());

        Institution institution = new Institution();
        institution.setName("Test institution");
        institutionRepository.save(institution);

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.now();

        AddDonationDTO addDonationDTO = new AddDonationDTO(
                1,
                categoryId,
                institution.getId(),
                "Street test",
                "city test",
                "zipCode test",
                date,
                time,
                "comment test");

        donationService.addDonation(addDonationDTO);
        assertEquals(donationRepository.findAll().stream()
                .filter(x -> x.getPickUpComment().equals("comment test")).count(), 1);
    }

    @Test
    void getDonation_donationNotExists_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> donationService.getDonation(1L));
    }

    @Test
    void updateDonation_donationExist_resultUpdateDonation() {
        Category category = new Category();
        category.setName("Test category");
        categoryRepository.save(category);

        List<Long> categoryId = new ArrayList<>();
        categoryId.add(category.getId());

        Institution institution = new Institution();
        institution.setName("Test institution");
        institutionRepository.save(institution);

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.now();

        AddDonationDTO addDonationDTO = new AddDonationDTO(
                1,
                categoryId,
                institution.getId(),
                "Street test",
                "city test",
                "zipCode test",
                date,
                time,
                "comment test");

        donationService.addDonation(addDonationDTO);

        Category newCategory = new Category();
        newCategory.setName("Test new category");
        categoryRepository.save(newCategory);

        List<Long> newCategoryId = new ArrayList<>();
        newCategoryId.add(newCategory.getId());

        Institution newInstitution = new Institution();
        newInstitution.setName("Test new institution");
        institutionRepository.save(newInstitution);

        LocalDate newDate = LocalDate.now().plusDays(2);
        LocalTime newTime = LocalTime.now().plusHours(1);

        Donation donation = donationRepository.findAll().stream()
                .filter(x -> x.getPickUpComment().equals("comment test"))
                .findFirst().orElse(new Donation());

        UpdateDonationDTO updateDonationDTO = new UpdateDonationDTO(
                donation.getId(),
                2,
                newCategoryId,
                newInstitution.getId(),
                "new street test",
                "new city test",
                "new zipCode test",
                newDate,
                newTime,
                "new comment test");

        donationService.updateDonation(updateDonationDTO);
        Donation updateDonation = donationRepository.getById(donation.getId());

        assertEquals(updateDonationDTO.getQuantity(), updateDonation.getQuantity());
        assertEquals(updateDonationDTO.getCategoriesId(), updateDonation.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        assertEquals(updateDonationDTO.getInstitutionId(), updateDonation.getInstitution().getId());
        assertEquals(updateDonation.getStreet(), updateDonation.getStreet());
        assertEquals(updateDonation.getCity(), updateDonation.getCity());
        assertEquals(updateDonation.getZipCode(), updateDonation.getZipCode());
        assertEquals(updateDonation.getPickUpDate(), updateDonation.getPickUpDate());
        assertEquals(updateDonation.getPickUpTime(), updateDonation.getPickUpTime());
        assertEquals(updateDonation.getPickUpComment(), updateDonation.getPickUpComment());
    }

    @Test
    void updateDonation_donationNotExists_throwAppRequestException() {
        Category category = new Category();
        category.setName("Test category");
        categoryRepository.save(category);

        List<Long> categoryId = new ArrayList<>();
        categoryId.add(category.getId());

        Institution institution = new Institution();
        institution.setName("Test institution");
        institutionRepository.save(institution);

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.now();

        UpdateDonationDTO updateDonationDTO = new UpdateDonationDTO(
                1L,
                2,
                categoryId,
                institution.getId(),
                "new street test",
                "new city test",
                "new zipCode test",
                date,
                time,
                "new comment test");

        Assertions.assertThrows(AppRequestException.class,
                () -> donationService.updateDonation(updateDonationDTO));
    }

    @Test
    void deleteDonation_donationExists_resultNull() {
        Category category = new Category();
        category.setName("Test category");
        categoryRepository.save(category);

        List<Long> categoryId = new ArrayList<>();
        categoryId.add(category.getId());

        Institution institution = new Institution();
        institution.setName("Test institution");
        institutionRepository.save(institution);

        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.now();

        AddDonationDTO addDonationDTO = new AddDonationDTO(
                1,
                categoryId,
                institution.getId(),
                "Street test",
                "city test",
                "zipCode test",
                date,
                time,
                "comment test");

        donationService.addDonation(addDonationDTO);

        Donation donation = donationRepository.findAll().stream()
                .filter(x -> x.getPickUpComment().equals("comment test"))
                .findFirst().orElse(new Donation());

        donationService.deleteDonation(donation.getId());

        assertNull(donationRepository.findById(donation.getId()).orElse(null));
    }

    @Test
    void deleteDonation_donationNotExists_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> donationService.deleteDonation(1L));
    }
}