package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberDelete.do1")
public class MemberDeleteServlet_ziji extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//입력한 값에 특수문자등을 처리해주기 위해서 인코딩해줘야 한다
		req.setCharacterEncoding("UTF-8");
		//해당하는 회원을 탈퇴시키기 위해 그 회원의 정보를
		
		
	/*	MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		
		try {
			BeanUtils.populate(member, req.getParameterMap());
		}catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}*/
		
		String message = null;
		Map<String, String> errors = new LinkedHashMap<>();
		
		
		//필수 데이터 검증 .. 아이디 비밀번호
		String mem_id=req.getParameter("mem_id");
		String mem_pass=req.getParameter("mem_pass");
		MemberVO member = new MemberVO();
		member.setMem_id(mem_id);
		member.setMem_pass(mem_pass);
		
		
		if(StringUtils.isBlank(member.getMem_id())) {
			message = "아이디 누락";
		}
		if(StringUtils.isBlank(member.getMem_pass())) {
			message = "비밀번호 누락";
		}
		
		
		
		
	}
	
}
