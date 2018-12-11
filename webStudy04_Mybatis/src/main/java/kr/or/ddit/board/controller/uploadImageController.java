package kr.or.ddit.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.message.MimeAttachmentSet;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.UploadImageVO;

@CommandHandler
public class uploadImageController{

	@URIMapping("/board/uploadImage.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String saveFolderUrl = "/boardImages";
		File folder = new File(req.getServletContext().getRealPath(saveFolderUrl));
		
		if(!folder.exists())folder.mkdirs();
		
		if(req instanceof FileUploadRequestWrapper) {
			FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("upload");
			
			UploadImageVO vo = new UploadImageVO();
			if(fileItem!=null) {
				String savename = UUID.randomUUID().toString();
				File savefile = new File(folder, savename);
				
				try (InputStream in = fileItem.getInputStream();){
					FileUtils.copyInputStreamToFile(in, savefile);
				}
			}else {
					Map<String, Object> error = new HashMap<>();
					error.put("number", 400);
					error.put("message", "업로드된 이미지가 없습니다");
 				}
				
				try(
					PrintWriter out = resp.getWriter();
				){
					ObjectMapper mapper = new ObjectMapper();
					mapper.writeValue(out, vo);
				}
	}
		return null;

	}
	}
