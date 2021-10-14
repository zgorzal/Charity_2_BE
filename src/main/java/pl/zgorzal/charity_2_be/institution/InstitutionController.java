package pl.zgorzal.charity_2_be.institution;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {
    private final InstitutionService institutionService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void addInstitution(@Valid @RequestBody Institution institution) {
        institutionService.addInstitution(institution);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public Institution getInstitution(@PathVariable Long id) {
        return institutionService.getInstitution(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public void updateInstitution(@Valid @RequestBody Institution institution) {
        try {
            institutionService.updateInstitution(institution);
        } catch (AppRequestException e) {
            throw new AppRequestException("Intytucja o id " + institution.getId() + " nie istnieje");
        } catch (Exception e) {
            throw new AppRequestException("Podana instytucja już istnieje");
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteInstitution(@PathVariable Long id) {
        institutionService.deleteInstitution(id);
    }
}
