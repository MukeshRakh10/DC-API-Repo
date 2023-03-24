package in.mk.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.mk.co.exception.handler.CustomHandler;
import in.mk.dc.dto.EductionDetailsDTO;
import in.mk.dc.dto.IncomeDetailsDTO;
import in.mk.dc.dto.KidsInfoDTO;
import in.mk.dc.dto.PlanSelectionDTO;
import in.mk.dc.dto.SummaryDTO;
import in.mk.dc.service.IDCService;

@RestController
@CrossOrigin("*")
public class DcRestController {

	@Autowired
	IDCService dcService;

	@GetMapping("/kafka/produces")
	public ResponseEntity<String> produceKafkaMsgStreaming() throws CustomHandler {
		String msg;
		msg = dcService.produceKafkaMsgStreaming();
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@GetMapping("/create/case/{appId}")
	public ResponseEntity<PlanSelectionDTO> createCase(@PathVariable Long appId) {
		PlanSelectionDTO palnSelection = dcService.createCase(appId);
		return new ResponseEntity<>(palnSelection, HttpStatus.OK);
	}

	@PostMapping("/apply/plan")
	public ResponseEntity<Long> applyPlan(@RequestBody PlanSelectionDTO plan) {
		Long caseId = dcService.updateCitizenPlan(plan);
		return new ResponseEntity<Long>(caseId, HttpStatus.OK);
	}

	@PostMapping("/save/income")
	public ResponseEntity<Long> saveIncome(@RequestBody IncomeDetailsDTO incomeDetailsDTO) {
		Long caseId = dcService.saveIncomeDetails(incomeDetailsDTO);
		return new ResponseEntity<Long>(caseId, HttpStatus.OK);
	}

	@PostMapping("/save/education")
	public ResponseEntity<Long> saveEducation(@RequestBody EductionDetailsDTO eduDto) {
		Long caseId = dcService.saveEducationDetails(eduDto);
		return new ResponseEntity<Long>(caseId, HttpStatus.OK);
	}

	@PostMapping("/save/kids")
	public ResponseEntity<Long> saveKids(@RequestBody KidsInfoDTO kidsInfo) {
		Long caseId = dcService.saveKidsDetails(kidsInfo);
		return new ResponseEntity<>(caseId, HttpStatus.OK);
	}

	@GetMapping("/get/summary/{appId}")
	public ResponseEntity<SummaryDTO> summary(@PathVariable Long appId) {
		SummaryDTO summary = dcService.getSummary(appId);
		return new ResponseEntity<>(summary, HttpStatus.OK);
	}
}
