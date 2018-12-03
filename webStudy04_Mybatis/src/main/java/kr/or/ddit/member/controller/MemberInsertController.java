package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.MemberVO;

//@WebServlet("/member/memberInsert.do") //URI
// 1. 파라미터 확보(특수문자 고려) -->
// 2. 검증(검증룰 : member 테이블의 스키마를 확인, 필수데이터 검증은 반드시!!) -->
// 3. 통과 -->
// 	1) 로직객체와의 의존관계 형성 -->
// 	2) 로직 선택(registMember) -->
//		PKDUPLICATED : memberForm.jsp 로 이동, foward(메시지, 기존 입력데이터 공유) -->
// 		OK : memberList.jsp로 이동, redirect -->
// 		FAILED : memberForm.jsp 로 이동, forward(기존 입력데이터, 메시지 공유) -->
// 4. 불통 -->
// 	memberForm.jsp 로 이동, forward(기존 입력데이터, 검증 결과 메시지 공유) -->

public class MemberInsertController implements ICommandHandler {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
		String view = null;
		if("get".equalsIgnoreCase(method)) {
			view = doGet(req, resp);
		}else if("post".equalsIgnoreCase(method)) {
			view = doPost(req, resp);
		}else {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return view;
	}
	
	protected String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "member/memberForm";
//		RequestDispatcher rd = req.getRequestDispatcher(view);
//		rd.forward(req, resp);
		return view;
	}
	
	protected String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
		MemberVO member = new MemberVO(); //command Object
		req.setAttribute("member", member);
//		member.setMem_id(req.getParameter("mem_id"));
		try {
			BeanUtils.populate(member, req.getParameterMap());//파라키터맵의 사이즈는 0
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
		
		
//		<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request" />
//		<jsp:setProperty property="mem_id" name="member" param="mem_id"/> 
		
		String goPage = null;
//		boolean redirect = false;
		String message = null;
		Map<String, String> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(member, errors);
		System.err.println(errors.size());

		// 	if(errors.size()>0){
		if (valid) {
			IMemberService service = new MemberServiceImpl();
			ServiceResult result = service.registMember(member);
			switch (result) {
			case PKDUPLICATED:
				goPage = "member/memberForm";
				message = "아이디 중복이니까 바꾸세요.";
				break;
			case FAILED:
//				goPage = "/WEB-INF/views/member/memberForm.jsp";
				goPage = "member/memberForm";
				message = "서버 오류로 인한 실패, 잠시 뒤에 다시 해주세요";
				break;
			case OK:
				goPage = "redirect:/member/memberList.do";
				break;
			}
			req.setAttribute("message", message);
		} else {
			goPage = "member/memberForm";
		}
		return goPage;
//		if (redirect) {
//			resp.sendRedirect(req.getContextPath() + goPage);
//		} else {
//			RequestDispatcher rd = req.getRequestDispatcher(goPage);
//			rd.forward(req, resp);
//		}
	}
	
	
	private boolean validate(MemberVO member, Map<String, String> errors){
		boolean valid = true;
		if(StringUtils.isBlank(member.getMem_id())){ 
			valid=false; 
			errors.put("mem_id", "회원아이디 누락");
		}
		if(StringUtils.isBlank(member.getMem_pass())){ 
			valid=false; 
			errors.put("mem_pass", "비밀번호 누락");
		}
		if(StringUtils.isBlank(member.getMem_name())){ 
			valid=false; 
			errors.put("mem_name", "회원명 누락");
		}
		if(StringUtils.isNotBlank(member.getMem_bir())){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			// formatting : 특정 타입의 데이터를 일정 형식의 문자열로 변환. String
			// parsing : 일정한 형식의 문자열을 특정 타입의 데이터로 변환. Date
			try{
				formatter.parse(member.getMem_bir()); 
			}catch(ParseException e){//checked Exception
				valid = false;
				errors.put("mem_bir", "날짜 형식 확인");
			}
		}
		return valid;
	}
}
