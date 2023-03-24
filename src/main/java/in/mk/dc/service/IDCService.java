package in.mk.dc.service;

import in.mk.dc.dto.EductionDetailsDTO;
import in.mk.dc.dto.IncomeDetailsDTO;
import in.mk.dc.dto.KidsInfoDTO;
import in.mk.dc.dto.PlanSelectionDTO;
import in.mk.dc.dto.SummaryDTO;

public interface IDCService {

	public String produceKafkaMsgStreaming();
	
	public PlanSelectionDTO createCase(Long appId);

	public Long updateCitizenPlan(PlanSelectionDTO planSelection);

	public Long saveIncomeDetails(IncomeDetailsDTO incomeDetails);

	public Long saveEducationDetails(EductionDetailsDTO educationDetails);

	public Long saveKidsDetails(KidsInfoDTO kidsDetails);

	public SummaryDTO getSummary(Long appId);



}
