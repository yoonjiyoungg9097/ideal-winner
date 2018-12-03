package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author 윤지영
 * @since 2018. 12. 3.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 3.      윤지영       파일 메타데이터를 가진 VO
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
@Data
public class FileVO implements Serializable{
	
	private String originalFilename;
	private String saveFilename;
	private String saveFileUrl;//서버사이드 방식의 절대경로만 있다
	private String saveFilePath;
	
	private long filesize;
	private String filemime;
	
	private String uploader;
	
	
}
