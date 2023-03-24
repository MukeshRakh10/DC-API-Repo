package in.mk.dc.dto;

import java.util.List;

import lombok.Data;

@Data
public class KidsInfoDTO {
	
	private List<KidsDetailsDTO> kids;
	private Long caseId;
}
