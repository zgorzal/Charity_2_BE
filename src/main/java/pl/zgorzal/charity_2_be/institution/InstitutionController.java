package pl.zgorzal.charity_2_be.institution;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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

    @GetMapping
    public List<Institution> getAllInstitution() {
        return institutionService.getAllInstitution();
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    public void updateInstitution(@Valid @RequestBody Institution institution) {
        institutionService.updateInstitution(institution);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteInstitution(@PathVariable Long id) {
        institutionService.deleteInstitution(id);
    }
}
