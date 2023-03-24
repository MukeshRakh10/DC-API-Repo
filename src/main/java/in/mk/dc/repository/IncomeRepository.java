package in.mk.dc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.mk.dc.entity.IncomeDetails;

public interface IncomeRepository extends JpaRepository<IncomeDetails, Long>{
	
	public IncomeDetails findByCaseId(Long caseId);

}
