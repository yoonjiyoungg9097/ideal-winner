package kr.or.ddit.common.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.AuthenticateServiceImpl;
import kr.or.ddit.member.service.IAuthenticateService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.utils.CookieUtil;
import kr.or.ddit.utils.CookieUtil.TextType;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class LoginController {
	IAuthenticateService service = new AuthenticateServiceImpl();

	@URIMapping(value = "redirect:/login/loginCheck.do", method = HttpMethod.POST)
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		// 아이디나 비밀번호에 특수문자라 있을 경우 처리해주는 부분
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String mem_id = req.getParameter("mem_id");
		String mem_pass = req.getParameter("mem_pass");
		String idChecked = req.getParameter("idChecked");
		String goPage = null; // 페이지 이동

		if (mem_id == null || mem_id.trim().length() == 0 || mem_pass == null || mem_pass.trim().length() == 0) {
//	 		goPage = "/login/loginForm.jsp?error=1";
			goPage = "redirect:/login/loginForm.jsp";
//	 		session.setAttribute("error", 1); //error는 이름 그에 해당하는 값=1
			session.setAttribute("message", "아이디나 비번 누락");
		} else {
//	 		if(id.equals(pass)){
			Object result = service.authenticate(new MemberVO(mem_id, mem_pass));
			if (result instanceof MemberVO) {// result 가 MemberVO타입인지 아닌지
				goPage = "redirect:/"; // 클라이언트 사이드
				int maxAge = 0;
				Cookie cookie = null;
				session.setAttribute("authMember", result);
				if ("idSaved".equals(idChecked)) {
					maxAge = 60 * 60 * 24 * 7;
				}
				Cookie idCookie = CookieUtil.createCookie("idCookie", mem_id, req.getContextPath(), TextType.PATH,
						maxAge);
				resp.addCookie(idCookie);
//	 			if(StringUtils.isNotBlank(request.getParameter("idChecked"))){
//	 				maxAge = 60*60*24*7;
//	 				cookie = CookieUtil.createCookie("idCookie", mem_id, request.getContextPath(), TextType.PATH, maxAge);
//	 			}else {
//	 				cookie = CookieUtil.createCookie("idCookie", mem_id, request.getContextPath(), TextType.PATH, maxAge);
//	 			}
//	 			response.addCookie(cookie);
			} else if (result == ServiceResult.PKNOTFOUND) {// 비번이 틀림
//	 			goPage = "/login/loginForm.jsp?error=1";
				goPage = "redirect:/login/loginForm.jsp";
//	 			session.setAttribute("error", 1);
				session.setAttribute("message", "존재하지 않는 회원");
			} else {
				goPage = "redirect:/login/loginForm.jsp";
//	 			session.setAttribute("error", 1);
				session.setAttribute("message", "비번 오류로 인증 실패");
			}
		}

		return goPage;
	}

	@URIMapping(value = "/login/loginout.do", method = HttpMethod.GET)
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
// 	session.removeAttribute("authMember");
	session.invalidate();
	// 이동(index.jsp, redirect)
	return "redirect:/";
	}
}
