package in.mk.dc.dto;

import lombok.Data;

@Data
public class EductionDetailsDTO {

	private Long educationId;
	private Long caseId;
	private String highestDegree;
	private Integer educationYear;
	private String universityName;

}
