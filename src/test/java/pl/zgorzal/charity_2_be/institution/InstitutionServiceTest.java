package pl.zgorzal.charity_2_be.institution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zgorzal.charity_2_be.exception.AppRequestException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InstitutionServiceTest {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Test
    void addInstitution_correctInstitution_resultInstitution() {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionService.addInstitution(institution);
        assertNotNull(institutionService.getInstitution(institution.getId()));
    }

    @Test
    void addInstitution_institutionExists_throwException() {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionService.addInstitution(institution);

        Institution newInstitution = new Institution();
        newInstitution.setName("test");
        newInstitution.setDescription("test test");

        Assertions.assertThrows(Exception.class,
                () -> institutionService.addInstitution(newInstitution));
    }

    @Test
    void getInstitution_institutionExists_resultInstitution() {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionService.addInstitution(institution);

        Institution findInstitution = institutionService.getInstitution(institution.getId());
        assertNotNull(findInstitution);
    }

    @Test
    void getInstitution_institutionNotExists_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> institutionService.getInstitution(1L));
    }

    @Test
    void updateInstitution_institutionExists_resultNewInstitutionName() {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionService.addInstitution(institution);

        String newName = "new name";
        institution.setName(newName);
        institutionService.updateInstitution(institution);

        assertEquals(institutionService.getInstitution(institution.getId()).getName(), newName);
    }

    @Test
    void updateInstitution_institutionNotExists_throwAppRequestException() {
        Institution institution = new Institution();
        institution.setId(1L);
        institution.setName("test");
        institution.setDescription("test test");

        Assertions.assertThrows(AppRequestException.class,
                () -> institutionService.updateInstitution(institution));
    }

    @Test
    void deleteInstitution_institutionExists_resultNull() {
        Institution institution = new Institution();
        institution.setName("test");
        institution.setDescription("test test");
        institutionService.addInstitution(institution);

        institutionService.deleteInstitution(institution.getId());

        assertNull(institutionRepository.findById(institution.getId()).orElse(null));
    }

    @Test
    void deleteInstitution_institutionNotExists_throwAppRequestException() {
        Assertions.assertThrows(AppRequestException.class,
                () -> institutionService.deleteInstitution(1L));
    }
}