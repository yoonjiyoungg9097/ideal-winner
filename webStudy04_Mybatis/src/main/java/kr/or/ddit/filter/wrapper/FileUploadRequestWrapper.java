package kr.or.ddit.filter.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

public class FileUploadRequestWrapper extends HttpServletRequestWrapper {
	private Map<String, String[]> parameterMap;
	private Map<String, List<FileItem>> fileItemMap;

	public FileUploadRequestWrapper(HttpServletRequest request) throws IOException {
		this(request, -1, null);
	}
	
	public FileUploadRequestWrapper(HttpServletRequest request, int sizeThreshold, File repository) throws IOException {
		super(request);
		
		parameterMap = new LinkedHashMap<>();
		parameterMap.putAll(request.getParameterMap());
		fileItemMap = new LinkedHashMap<>();
		parseRequest(request, sizeThreshold, repository);
	}

	private void parseRequest(HttpServletRequest request, int sizeThreshold, File repository) throws IOException {
//		문자 Part는 parameterMap 에 누적
//		파일 Part는 fileItemMap 에 누적
		// 1. commons-fileupload 라이브러리 추가
		// 2. 파일 업로드 핸들러 객체 생성
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		if(sizeThreshold!=-1) {
			fileItemFactory.setSizeThreshold(sizeThreshold);
		}
		if(repository!=null) {
			fileItemFactory.setRepository(repository);
		}

		ServletFileUpload handler = new ServletFileUpload(fileItemFactory);

//				ServletFileUpload handler = new ServletFileUpload();//임시 저장 위치를 설정하지 않고 변경하고 싶을때??
		// 3. 핸들러 객체를 이용해 현재 요청 파싱 (Part -> FileItem)
		request.setCharacterEncoding("UTF-8");
		try {
			List<FileItem> fileItems = handler.parseRequest(request);
			// 4. FileItem 들을 대상으로 반복 실행
			if (fileItems != null) {
				for (FileItem item : fileItems) {
					String partname = item.getFieldName();// 파라미터명
					if (item.isFormField()) {// 파일데이터인지
						// 5. 일반 문자열 기반의 FileItem 에 대한 처리와
						String parameterValue = item.getString(request.getCharacterEncoding());
						String[] alreadyValues = parameterMap.get(partname);
						String[] values = null;
						if (alreadyValues == null) {// 기존의 같은이름을 쓰는 데이터가 없다
							values = new String[1]; // 1개짜리 배열
						} else {// 기존의 같은이름을 쓰는 데이터가 있다
							values = new String[alreadyValues.length + 1];
							System.arraycopy(alreadyValues, 0, values, 0, alreadyValues.length);
						}
						values[values.length - 1] = parameterValue;
						parameterMap.put(partname, values);
					} else {
						// 파일을 선택하지 않으면 비어있는 part를 skip
						if (StringUtils.isBlank(item.getName())) {// 파일이 선택되지 않았는데 만들어질때??????
							continue;
						}
						// 파일 기반의 FileItem 에 대한 처리를 수행
						List<FileItem> alreadyItems = fileItemMap.get(partname);

						if (alreadyItems == null) {
							alreadyItems = new ArrayList<>();
						}
						alreadyItems.add(item);

						fileItemMap.put(partname, alreadyItems);
					}

				} // for end
			} // if end

		} catch (FileUploadException e) {
			throw new IOException(e);
		}

	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}
	
	@Override
	public String getParameter(String name) {
		String[] values = parameterMap.get(name);
		if(values==null) {
			return null;
		}else {
			return values[0];
		}
	}

	
	@Override
	public String[] getParameterValues(String name) {
		return parameterMap.get(name);
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		final Iterator<String>it = parameterMap.keySet().iterator();//객체의 생존범위가 다르기 때문에?????????????
		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {//hasnext
				return it.hasNext();
			}
			
			@Override
			public String nextElement() {//next
				return it.next();
			}
		};
	}
	
	public FileItem getFileItem(String partname){
		List<FileItem>itemList = fileItemMap.get(partname);
		if(itemList!=null && itemList.size()>0) {
			return itemList.get(0);
		}else {
			return null;
		}
	}
	
	public List<FileItem> getFileItems(String partname){
		return fileItemMap.get(partname);
	}
	
	public Map<String, List<FileItem>> getFileItemMap() {
		return fileItemMap;
	}
	
	public Enumeration<String> getFileItemNames(){
		final Iterator<String>it = fileItemMap.keySet().iterator();//객체의 생존범위가 다르기 때문에?????????????
		return new Enumeration<String>() {
			@Override
			public boolean hasMoreElements() {//hasnext
				return it.hasNext();
			}
			
			@Override
			public String nextElement() {//next
				return it.next();
			}
		};
	}
	
	public void deleteAllTempFile(){
		for (Entry<String, List<FileItem>>entry : fileItemMap.entrySet()) {
			for(FileItem tmp : entry.getValue()) {
				tmp.delete();
			}
		}
	}
}















