package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {//필터를 웹 콘테이너내에 생성한 후 초기화할 때 호출한다.
		logger.info("{} 필터 초기화", getClass().getSimpleName());//getClass()는 객체가 속하는 클래스의 정보를 알아내는 메소드이다.
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {//매번 요청이 들어올때마다 실제 필터링 해주는 부분
		//체인을 따라 다음에 존재하는 필터로 이동한다. 체인의 가장 마지막에는 클라이언트가 요청한 최종 자원이 위치한다.
		logger.info("요청 필터링");
		chain.doFilter(request, response); // --> 다음 필터나 최종 자원 쪽으로 제어 이동    //response를 이용하여 응답의 필터링 작업 수행
		logger.info("응답 필터링");
	}

	@Override
	public void destroy() {//필터가 웹 콘테이너에서 삭제될 때 호출된다.
		// 주로 필터가 사용한 자원을 반납
		logger.info("{} 필터 소멸", getClass().getSimpleName());
	}

}
