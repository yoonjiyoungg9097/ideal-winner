package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/member/memberDelete.do")
//public class MemberDeleteServlet extends HttpServlet {
public class MemberDeleteController implements ICommandHandler {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mem_id = req.getParameter("mem_id");
		String mem_pass = req.getParameter("mem_pass");
		if (StringUtils.isBlank(mem_id) || StringUtils.isBlank(mem_pass)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		IMemberService service = new MemberServiceImpl();
		ServiceResult result = service.removeMember(new MemberVO(mem_id, mem_pass));
		String view = null;
//		boolean redirect = false;
		String message = null;

		switch (result) {
		case INVALIDPASSWORD:
			message = "비번 오류";
//			view = "/member/memberView.do?who="+mem_id;
			view = "redirect:/member/mypage.do";//탈퇴할때 비밀번호 입력받지만 입력에 실패했을 경우에는 틀린 비밀번호를 알려줄 필요가 없기 때문에 redirect
			break;
		case FAILED:
			message = "서버 오류 미안합니다";
//			view = "/member/memberView.do?who="+mem_id;
			view = "redirect:/member/mypage.do";
			break;
		default:
//			view = "/member/memberList.do";
			view = "redirect:/common/message.jsp";
			message = "탈퇴약관 : 일주일이내에서 절대 같은 아이디로 재가입 불가!!";
			req.getSession().setAttribute("goLink", "/");
			req.getSession().setAttribute("isRemoved", new Boolean(true));
//			req.getSession().invalidate();
			break;
		}
		req.getSession().setAttribute("message", message);
		return view;
//		if (redirect) {
//			resp.sendRedirect(req.getContextPath() + view);
//		} else {
//			RequestDispatcher rd = req.getRequestDispatcher(view);
//			rd.forward(req, resp);
//		}
	}
}
