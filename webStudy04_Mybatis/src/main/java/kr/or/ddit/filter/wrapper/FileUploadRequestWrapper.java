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
	//문자열이 아닌 파라미터맵을 담아주기위한 맵
	private Map<String, String[]> parameterMap;
	//파트로나눈것중에 input태그의 타입이 file일 경우 요기에 담아줌
	private Map<String, List<FileItem>> fileItemMap;

	public FileUploadRequestWrapper(HttpServletRequest request) throws IOException {
		this(request, -1, null);
	}
	
	public FileUploadRequestWrapper(HttpServletRequest request, int sizeThreshold, File repository) throws IOException {
		super(request);
		
		parameterMap = new LinkedHashMap<>();//생성
		parameterMap.putAll(request.getParameterMap());//input태그의 모든 파라미터를 가지고옴
		fileItemMap = new LinkedHashMap<>();
		parseRequest(request, sizeThreshold, repository);//parseRequest 메소드 호출

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
		request.setCharacterEncoding("UTF-8");//한글이 포함된 파일이 올수도 있으니까 인코딩

		try {
			//req즉 요청받은 파라미터를 Fileitem으로 파싱해줌.
			List<FileItem> fileItems = handler.parseRequest(request);
			// 4. FileItem 들을 대상으로 반복 실행
			if (fileItems != null) {
				for (FileItem item : fileItems) {
					String partname = item.getFieldName();// 파라미터명
					//파트에 담긴 input태그의 타입이 file이면 펄스, 아니면 트루
					if (item.isFormField()) {// 파일데이터인지
						// 5. 일반 문자열 기반의 FileItem 에 대한 처리와
						String parameterValue = item.getString(request.getCharacterEncoding());
						String[] alreadyValues = parameterMap.get(partname);
						String[] values = null;
						if (alreadyValues == null) {// 기존의 같은이름을 쓰는 데이터가 없다
							//즉 values의 배열 생성 크기는 1
							values = new String[1]; // 1개짜리 배열
						} else {// 기존의 같은이름을 쓰는 데이터가 있다
							values = new String[alreadyValues.length + 1];
							System.arraycopy(alreadyValues, 0, values, 0, alreadyValues.length);
						}
						//1회차일경우 values의 0번째 방에 utf-8담아줌 
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
		//컨트롤러에서 리퀘스트.getParamterMap하면 이곳의 맵이 호출되서 값을주는거임
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















