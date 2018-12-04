package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter 구현
 * 1. javax.servlet.Filter 구현체 작성
 * 2. lifecycle 메소드 : init, destroy
 * 	    요청 filtering 메소드 : doFilter - ** chain.doFilter 메소드 호출전에 요청 필터링
 * 									 ** chain.doFilter 메소드 호출전에 응답 필터링
 * 									  요청이 필터링 되는 순서와 응답이 필터링 되는 순서는 역순이 된다
 * 3. W.A.S 에 해당 필터를 등록(web.xml -> filter) : WAS에 의해 FilterChain 이 형성된다
 * 4. 필터링 할 수 있는 요청과의 매핑 설정(web.xml -> filter-mapping)
 */
//@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {
	private String encoding = "UTF-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
