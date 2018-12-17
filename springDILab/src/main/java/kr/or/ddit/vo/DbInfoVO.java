package kr.or.ddit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbInfoVO {
	private String driverClassName;
	private String url;
	private String user;
	private String password;
	private String initialSize;
	private String maxActive;
	private String maxWait;
}
