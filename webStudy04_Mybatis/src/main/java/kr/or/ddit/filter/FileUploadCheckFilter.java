package kr.or.ddit.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;

public class FileUploadCheckFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 필터 초기화");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {//request상위타입으로 ?????????????
		
		
		// 요청 필터링 
		String contentType = request.getContentType();
		if(contentType!=null && contentType.startsWith("multipart/")) {
			HttpServletRequest req = (HttpServletRequest)request;
			int sizeThreshole = 10240;
			File repository = new File("d:/temp");
			FileUploadRequestWrapper wrapper = new FileUploadRequestWrapper(req, sizeThreshole, repository);
			logger.info("{}에서 multipart reauest 가  {}로 변경된다", getClass().getSimpleName(), wrapper.getClass().getSimpleName());
					chain.doFilter(wrapper, response);
			//servletRequest는 http서블릿 리퀘스트보다 상위 개념이므로 다운캐스팅이 필요하다
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		logger.info("{} 필터 소멸");
	}

}
