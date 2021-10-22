package pl.zgorzal.charity_2_be.donation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zgorzal.charity_2_be.category.Category;
import pl.zgorzal.charity_2_be.category.CategoryService;
import pl.zgorzal.charity_2_be.donation.DTO.AddDonationDTO;
import pl.zgorzal.charity_2_be.donation.DTO.UpdateDonationDTO;
import pl.zgorzal.charity_2_be.exception.AppRequestException;
import pl.zgorzal.charity_2_be.institution.Institution;
import pl.zgorzal.charity_2_be.institution.InstitutionService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {
    private final DonationRepository donationRepository;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;

    public void addDonation(AddDonationDTO addDonationDTO) {
        List<Category> categories = addDonationDTO.getCategoriesId().stream()
                .map(categoryService::getCategory)
                .collect(Collectors.toList());

        Institution institution = institutionService.getInstitution(addDonationDTO.getInstitutionId());

        Donation donation = new Donation(
                addDonationDTO.getQuantity(),
                categories,
                institution,
                addDonationDTO.getStreet(),
                addDonationDTO.getCity(),
                addDonationDTO.getZipCode(),
                addDonationDTO.getPickUpDate(),
                addDonationDTO.getPickUpTime(),
                addDonationDTO.getPickUpComment());

        donationRepository.save(donation);
    }

    public Donation getDonation(Long id) {
        return donationRepository.findById(id).orElseThrow(() -> new AppRequestException("Donation not found"));
    }

    public void updateDonation(UpdateDonationDTO updateDonationDTO) {
        Donation donation = getDonation(updateDonationDTO.getId());

        List<Category> categories = updateDonationDTO.getCategoriesId().stream()
                .map(categoryService::getCategory)
                .collect(Collectors.toList());

        Institution institution = institutionService.getInstitution(updateDonationDTO.getInstitutionId());

        donation.setQuantity(updateDonationDTO.getQuantity());
        donation.setCategories(categories);
        donation.setInstitution(institution);
        donation.setStreet(updateDonationDTO.getStreet());
        donation.setCity(updateDonationDTO.getCity());
        donation.setZipCode(updateDonationDTO.getZipCode());
        donation.setPickUpDate(updateDonationDTO.getPickUpDate());
        donation.setPickUpTime(updateDonationDTO.getPickUpTime());
        donation.setPickUpComment(updateDonationDTO.getPickUpComment());

        donationRepository.save(donation);
    }

    public void deleteDonation(Long id) {
        Donation donation = getDonation(id);
        donation.setCategories(new ArrayList<>());
        donationRepository.delete(donation);
    }
}
