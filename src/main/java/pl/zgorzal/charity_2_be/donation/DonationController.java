package pl.zgorzal.charity_2_be.donation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.zgorzal.charity_2_be.donation.DTO.AddDonationDTO;
import pl.zgorzal.charity_2_be.donation.DTO.GetAllQuantityDonationDTO;
import pl.zgorzal.charity_2_be.donation.DTO.GetNumberOfDonationDTO;
import pl.zgorzal.charity_2_be.donation.DTO.UpdateDonationDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donation")
public class DonationController {

    private final DonationService donationService;

//    @Secured("ROLE_USER")
    @PostMapping
    public void addDonation(@Valid @RequestBody AddDonationDTO addDonationDTO) {
        donationService.addDonation(addDonationDTO);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public Donation getDonation(@PathVariable Long id) {
        return donationService.getDonation(id);
    }

    @GetMapping("/quantity")
    public GetAllQuantityDonationDTO getAllQuantityDonation() {
        return donationService.getAllQuantityDonation();
    }

    @GetMapping("/count")
    public GetNumberOfDonationDTO getNumberOfDonation() {
        return donationService.getNumberOfDonation();
    }

    @Secured("ROLE_USER")
    @PutMapping
    public void updateDonation(@Valid @RequestBody UpdateDonationDTO updateDonationDTO) {
        donationService.updateDonation(updateDonationDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
    }
}
