package pl.zgorzal.charity_2_be.institution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InstitutionRepositoryTest {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    void findByName_institutionExists_resultInstitution() {
        String name = "institution name";
        Institution addInstitution = new Institution();
        addInstitution.setName(name);
        institutionRepository.save(addInstitution);
        Institution institution = institutionRepository.findByName(name);
        assertNotNull(institution);
    }

    @Test
    void findByName_institutionNotExist_resultNull() {
        String name = "Not exist";
        Institution institution = institutionRepository.findByName(name);
        assertNull(institution);
    }

}