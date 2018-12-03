package kr.or.ddit.web.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

public class FileUploadServlert_2_5 extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]>parameterMap = new LinkedHashMap<>(); //map의 entry는 문자열 if문안에서 넣어준다
		Map<String, List<FileItem>> fileItemMap = new LinkedHashMap<>();
		
//		InputStream in = req.getInputStream();
		// 1. commons-fileupload 라이브러리 추가
		// 2. 파일 업로드 핸들러 객체 생성
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(10240, new File("d:/temp"));//10240->10kb
		
		ServletFileUpload handler = new ServletFileUpload(fileItemFactory);
		
//		ServletFileUpload handler = new ServletFileUpload();//임시 저장 위치를 설정하지 않고 변경하고 싶을때??
		// 3. 핸들러 객체를 이용해 현재 요청 파싱 (Part -> FileItem)
		req.setCharacterEncoding("UTF-8");
		try {
			List<FileItem>fileItems = handler.parseRequest(req);
			// 4. FileItem 들을 대상으로 반복 실행
			if(fileItems!=null) {
				for(FileItem item : fileItems) {
					String partname = item.getFieldName();//파라미터명
					if(item.isFormField()) {//파일데이터인지
						// 5. 일반 문자열 기반의 FileItem 에 대한 처리와
						String parameterValue = item.getString(req.getCharacterEncoding());
						String[] alreadyValues = parameterMap.get(partname);
						String[] values = null;
						if(alreadyValues==null) {//기존의 같은이름을 쓰는 데이터가 없다
							values = new String[1]; //1개짜리 배열
						}else {//기존의 같은이름을 쓰는 데이터가 있다
							values = new String[alreadyValues.length+1];
							System.arraycopy(alreadyValues, 0, values, 0, alreadyValues.length);
						}
						values[values.length-1]=parameterValue;
						parameterMap.put(partname, values);
					}else {
						//파일을 선택하지 않으면 비어있는 part를 skip
						if(StringUtils.isBlank(item.getName())) {//파일이 선택되지 않았는데  만들어질때??????
							continue;
						}
						// 파일 기반의 FileItem 에 대한 처리를 수행
						List<FileItem>alreadyItems = fileItemMap.get(partname);
						
						if(alreadyItems==null) {
							alreadyItems = new ArrayList<>();
						}
						alreadyItems.add(item);
						
						fileItemMap.put(partname, alreadyItems);
					}	
					
				}//for end
			}//if end
			
			System.out.println(parameterMap.get("uploader")[0]);
			System.out.println(fileItemMap.get("uploadFile").get(0));
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
}
