package kr.or.ddit.vo;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadImageVO {
	private String fileName;
	private int uploaded;
	private String url;
	private Map<String, Object> error;
}
