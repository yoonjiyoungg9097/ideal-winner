package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardBookVO implements Serializable{
	private Long bo_no;		
	private String bo_writer;
	private String bo_pass;
	private String bo_ip;
	private String bo_content;
	private String bo_date;
}
