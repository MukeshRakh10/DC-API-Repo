package in.mk.dc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.mk.dc.entity.EducationDetails;

@Repository
public interface EducatonDetailsRepository extends JpaRepository<EducationDetails, Long>{
	EducationDetails findByCaseId(Long caseId);
}