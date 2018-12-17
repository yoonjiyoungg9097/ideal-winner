package kr.or.ddit.vo;

import java.io.Serializable;

import kr.or.ddit.validator.rules.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardBookVO implements Serializable{
	private Long bo_no;		
	@NotBlank(message="작성자 필수")
	private String bo_writer;
	@NotBlank(message="비밀번호 필수")
	private String bo_pass;
	private String bo_ip;
	@NotBlank(message="내용 필수")
	private String bo_content;
	private String bo_date;
}
