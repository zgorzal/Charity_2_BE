package pl.zgorzal.charity_2_be.institution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Institution findByName(String name);
}
