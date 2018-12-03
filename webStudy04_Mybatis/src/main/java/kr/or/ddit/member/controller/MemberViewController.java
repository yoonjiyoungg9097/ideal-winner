package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/member/memberView.do")
//public class MemberViewServlet extends HttpServlet{
public class MemberViewController implements ICommandHandler{
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mem_id = req.getParameter("who");
		if (StringUtils.isBlank(mem_id)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;//**경우에따라 view가 결정되지 않을 수도 있다
		}
		IMemberService service = new MemberServiceImpl();
		MemberVO member = service.retrieveMember(mem_id);
		String view = "member/memberView";
		req.setAttribute("member", member);
//		RequestDispatcher rd = req.getRequestDispatcher(view);
//		rd.forward(req, resp);
		return view;
	}

}
