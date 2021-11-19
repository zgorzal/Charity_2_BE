package pl.zgorzal.charity_2_be.institution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    public void addInstitution(Institution institution) {
        institutionRepository.save(institution);
    }

    public Institution getInstitution(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new AppRequestException("Institution not found"));
    }

    public List<Institution> getAllInstitution(){
        return institutionRepository.findAll();
    }

    public void updateInstitution(Institution institution) {
        Institution updateInstitution = getInstitution(institution.getId());
        updateInstitution.setName(institution.getName());
        updateInstitution.setDescription(institution.getDescription());
        institutionRepository.save(updateInstitution);
    }

    public void deleteInstitution(Long id) {
        Institution institution = getInstitution(id);
        institutionRepository.delete(institution);
    }
}
