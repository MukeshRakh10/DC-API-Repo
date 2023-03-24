package in.mk.dc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import in.mk.dc.dto.EductionDetailsDTO;
import in.mk.dc.dto.IncomeDetailsDTO;
import in.mk.dc.dto.KidsDetailsDTO;
import in.mk.dc.dto.KidsInfoDTO;
import in.mk.dc.dto.PlanSelectionDTO;
import in.mk.dc.dto.SummaryDTO;
import in.mk.dc.entity.CitizenApp;
import in.mk.dc.entity.DcCase;
import in.mk.dc.entity.EducationDetails;
import in.mk.dc.entity.IncomeDetails;
import in.mk.dc.entity.KidsDetails;
import in.mk.dc.entity.PlanSelection;
import in.mk.dc.repository.CitizenAppRepository;
import in.mk.dc.repository.DcCaseRepository;
import in.mk.dc.repository.EducatonDetailsRepository;
import in.mk.dc.repository.IncomeRepository;
import in.mk.dc.repository.KidsRepository;
import in.mk.dc.repository.PlanSelectionRepository;
import in.mk.dc.service.IDCService;

@Service
public class DCServiceImpl implements IDCService {

	@Autowired
	private CitizenAppRepository citizenAppRepo;
	@Autowired
	private DcCaseRepository caseRepo;
	@Autowired
	private PlanSelectionRepository planRepo;
	@Autowired
	private IncomeRepository incomeRepo;
	@Autowired
	private EducatonDetailsRepository eduRepo;

	@Autowired
	private KidsRepository kidsRepo;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	
	
	@Override
	public String produceKafkaMsgStreaming() {
		String msg = "";
		for(int i = 1 ; i <= 3;i++) {
			 msg = "Record Added In Kafka Queue Successfully !!!";
			 System.out.println("his-dc-topic"+ "Hi .... Welcome... DC- "+i);
			 kafkaTemplate.send("his-dc-topic", "Hi .... Welcome... DC- "+i);
		}
		return msg;
	}


	@Override
	public PlanSelectionDTO createCase(Long appId) {

		PlanSelectionDTO planSelDto = new PlanSelectionDTO();
		Optional<CitizenApp> findById = citizenAppRepo.findById(appId);

		if (findById.isPresent()) {
			Optional<DcCase> isCaseRecordPresent = caseRepo.findById(Long.parseLong(appId + ""));
			DcCase cas = new DcCase();
			if (!isCaseRecordPresent.isPresent()) {
				// create case
				cas.setAppId(Long.parseLong(appId + ""));
				cas = caseRepo.save(cas);
				planSelDto.setCaseId(cas.getCaseId());
			} else {
				planSelDto.setCaseId(isCaseRecordPresent.get().getCaseId());
			}
			List<PlanSelection> plans = planRepo.findAll();
			Map<Long, String> planMap = new HashMap<>();
			plans.forEach(plan -> {
				planMap.put(plan.getPlanId(), plan.getPlanName());
			});
			planSelDto.setPlansInfo(planMap);
		}
		return planSelDto;
	}

	@Override
	public Long updateCitizenPlan(PlanSelectionDTO planSelection) {
		Long caseId = planSelection.getCaseId();
		Long planId = planSelection.getPlanId();
		Optional<DcCase> findDcCase = caseRepo.findById(caseId);
		if (findDcCase.isPresent()) {
			DcCase dcCaseEntity = findDcCase.get();
			dcCaseEntity.setPlanId(planId);
			caseRepo.save(dcCaseEntity);
		}
		return caseId;
	}

	@Override
	public Long saveIncomeDetails(IncomeDetailsDTO incomeDetails) {
		
		Long caseId = incomeDetails.getCaseId();	
		Optional<IncomeDetails> incomeEntity = incomeRepo.findById(caseId);
		if(!incomeEntity.isPresent()) {
			IncomeDetails income = new IncomeDetails();
			BeanUtils.copyProperties(incomeDetails, income);
			incomeRepo.save(income);
		}
		return incomeDetails.getCaseId();
	}

	@Override
	public Long saveEducationDetails(EductionDetailsDTO eduDto) {
		Long caseId = eduDto.getCaseId();
		Optional<EducationDetails> eduEntity = eduRepo.findById(caseId);
		if(!eduEntity.isPresent()) {
			EducationDetails ed = new EducationDetails();
			BeanUtils.copyProperties(eduDto, ed);
			eduRepo.save(ed);
		}
		return eduDto.getCaseId();
	}

	@Override
	public Long saveKidsDetails(KidsInfoDTO kidsInfoDto) {
		Long caseId = kidsInfoDto.getCaseId();
		
		Optional<KidsDetails> kidsDetaiOptional = kidsRepo.findById(caseId);
		if(!kidsDetaiOptional.isPresent()) {
			List<KidsDetails> kidsEntity = new ArrayList<>();
			kidsInfoDto.getKids().forEach(dto -> {
				KidsDetails kid = new KidsDetails();
				BeanUtils.copyProperties(dto, kid);
				kid.setCaseId(caseId);
				kidsEntity.add(kid);
			});
			kidsRepo.saveAll(kidsEntity);
		}
		return caseId;
	}

	@Override
	public SummaryDTO getSummary(Long casseId) {
		Optional<DcCase> dcCase = caseRepo.findById(casseId);
		DcCase dcCaseEntity = dcCase.get();

		Long caseId = dcCaseEntity.getCaseId();
		Long planId = dcCaseEntity.getPlanId();
		Long appId = dcCaseEntity.getAppId();

		Optional<PlanSelection> plan = planRepo.findById(planId);
		String planName = plan.get().getPlanName();

		Optional<CitizenApp> citizenApp = citizenAppRepo.findById(appId);
		String fullName = citizenApp.get().getFullName();
		Long ssn = citizenApp.get().getSsn();

		IncomeDetails id = incomeRepo.findByCaseId(caseId);
		EducationDetails ed = eduRepo.findByCaseId(caseId);
		
		List<KidsDetails> kd = kidsRepo.findByCaseId(caseId);

		SummaryDTO summary = new SummaryDTO();
		summary.setPlanNmae(planName);
		summary.setFname(fullName);
		summary.setSsn(ssn);

		PlanSelectionDTO psdto = new PlanSelectionDTO();
		BeanUtils.copyProperties(plan, psdto);

		IncomeDetailsDTO idDto = new IncomeDetailsDTO();
		BeanUtils.copyProperties(id, idDto);

		EductionDetailsDTO edDto = new EductionDetailsDTO();
		BeanUtils.copyProperties(ed, edDto);

		summary.setPlanInfo(psdto);
		summary.setIncomeDetails(idDto);
		summary.setEducationDetails(edDto);
		
		List<KidsDetailsDTO> kdList = new ArrayList<>();
		kd.forEach(entity ->{
			KidsDetailsDTO dto = new KidsDetailsDTO();
			BeanUtils.copyProperties(entity, dto);
			kdList.add(dto);
		} );
		summary.setKindsDetails(kdList);
		

		return summary;
	}


}
