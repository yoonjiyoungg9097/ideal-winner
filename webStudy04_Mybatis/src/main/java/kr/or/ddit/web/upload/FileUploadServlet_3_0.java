package kr.or.ddit.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;

import javafx.scene.image.Image;
import kr.or.ddit.vo.FileVO;

//@WebServlet("/upload")
//@MultipartConfig /*part를 전처리 그안에 있는 문자열을 꺼내서 파라미터를 대신 만들어준다*/
public class FileUploadServlet_3_0 extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//post방식에서 한글깨지면 제일먼저
		req.setCharacterEncoding("UTF-8");
		
//		File saveFolder = new File("D:\\saveFiles"); 직접 생성해준 saveFiles의 경로
//		/saveFiles 에 저장 파일리소스로 저장한게 아니라 web리소스로 저장
		String saveUrl = "/saveFiles";
		String savePath = getServletContext().getRealPath(saveUrl);
		File saveFolder = new File(savePath);
		if(!saveFolder.exists()) {
			saveFolder.mkdirs(); //존재하지 않는 폴더라면 생성해주는 부분
		}
		
		String uploader = req.getParameter("uploader");
		Map<String, String[]>parameterMap = req.getParameterMap();
		System.out.println(uploader);
		System.out.println(parameterMap.size());//어떤 형태의 어떤 데이터가 들어가 있는지??
//		System.out.println(parameterMap.get("uploadFile"));
//		Part uploader = req.getPart("uploader");
		Part uploadFile = req.getPart("uploadFile");
		String filemime = uploadFile.getContentType();
		if(!StringUtils.startsWithIgnoreCase(filemime, "image/")) {//파일이 이미지가 아니라면 응답을 내보내주는 부분 파일이 이미지라면 이부분을 거치지 않는다
			resp.sendError(400);
			return;
		}
		
//		Content-Disposition: form-data; name="uploadFile"; filename="Penguins.jpg"
		String header = uploadFile.getHeader("Content-Disposition");
		int idx = header.lastIndexOf("filename=")+"filename=".length();
		String originalFilename = header.substring(idx).replace("\"", "");
		System.out.println(originalFilename);
		String savename = UUID.randomUUID().toString(); //새로운 문자열을 랜덤으로 생성하여 기존의 파일 이름을 변경하고 확장자도 없애준다 
		//그런 효과로 해킹의 위험에서 벗어난다
		
//		File saveFile = new File(saveFolder, originalFilename);//원본파일명을 대신해서 확장자를 뺀 파일명
		//Middle tier 에 파일의 body를 저장
		File saveFile = new File(saveFolder, savename);
		byte[] buffer = new byte[1024];//1KB = 1024buytes
		int pointer = -1;
		try(
			InputStream in = uploadFile.getInputStream();
			FileOutputStream fos = new FileOutputStream(saveFile);
		){
			while((pointer = in.read(buffer))!=-1) {
				fos.write(buffer, 0, pointer);
			}
		}
		
		Collection<Part> parts = req.getParts();
		System.out.println(parts.size());
		
		
		//Database 에 파일의 메타데이터를 저장 (메타데이터 : 누가 업로드했는지 원래마임은 뭔지 이런거?)
		// getPath()는 src/test 그대로 반환한 반면, getAbsolutePath()와 getCanonicalPath()는 현재 프로그램을 실행한 경로(D:\workspace\Test)를 포함하고 있다.
		long filesize = uploadFile.getSize();
		System.out.printf("데이터베이스에 저장할 메타데이터: 업로더(%s), 원본명:(%s), \n"+
				"파일크기(%d), 파일종류(%s), 저장위치(%s), \n 저장URL(%s)",
				uploader, originalFilename, filesize, filemime, saveFile.getAbsolutePath(), saveUrl +"/"+savename);
		FileVO fileVO = new FileVO();
		fileVO.setUploader(uploader);
		fileVO.setOriginalFilename(originalFilename);
		fileVO.setSaveFilename(savename);
		//웹리소스로 저장하는 경우에만 생성되는 메타데이터
		fileVO.setSaveFileUrl(saveUrl +"/"+savename);
		fileVO.setSaveFilePath(saveFile.getAbsolutePath());
		fileVO.setFilesize(filesize);
		fileVO.setFilemime(filemime);
		
		req.getSession().setAttribute("fileVO", fileVO);
		
		resp.sendRedirect(req.getContextPath()+"/13/fileForm.jsp");//최소한의 스코프 영역은 session redirect방식이기때문에 request의미 없다
		
	}
}
