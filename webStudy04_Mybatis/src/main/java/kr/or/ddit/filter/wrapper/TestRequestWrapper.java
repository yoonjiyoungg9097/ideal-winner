package kr.or.ddit.filter.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class TestRequestWrapper extends HttpServletRequestWrapper {
	//wrapper객체를 생성하려면 기본값을 넣어줘야한다 기본생성자가 없기때문에
	public TestRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		if("who".equals(name)) {//누군가가 request parameter로 who를 꺼낼때?????
			return "b001";
		}
		return super.getParameter(name);
	}

}
