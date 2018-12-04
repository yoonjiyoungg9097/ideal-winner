package kr.or.ddit.filter.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.vo.MemberVO;

/**
 * 
 * @author 윤지영
 * @since 2018. 12. 4.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2018. 12. 4.      윤지영      보호 자원에 접근하고 있는 유저에 대해 유저에 부여된 권한과 자원에 설정된 권한 매칭 여부 확인
 * Copyright (c) 2018 by DDIT All right reserved
 * </pre>
 */
public class AuthorizationFilter implements Filter {
	// 보호자원 : 권한들
	private Map<String, String[]> securedResources;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//securedResources에 어플리케이션영역에 담긴 값을 담아준다
		securedResources= (Map<String, String[]>) request.getServletContext().getAttribute(AuthenticationFilter.SECUREDRESOURCEATTR);
		//먼저 요청한 uri를 가지고온다
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		int cpLength = req.getContextPath().length();
		uri = uri.substring(cpLength).split(";")[0];
		
		boolean pass = true;
		//securedResources에 서 가져온값과 uri와 비교해준다
		if(securedResources.containsKey(uri)) {//보호되고 있는 자원
			MemberVO authMember = (MemberVO)req.getSession().getAttribute("authMember");
			String mem_auth = authMember.getMem_auth();
			String[] resAuthes = securedResources.get(uri);
			if(Arrays.binarySearch(resAuthes, mem_auth)<0) {//보호되어 있는 자원과 권한이 일치 하지 않을떼???
				pass = false;
			}
		}
		
		if(pass) {
			chain.doFilter(request, response);
		}else {//허가 되지 않은 유저
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		//그후 작업하시오
		
	}

	@Override
	public void destroy() {

	}

}
