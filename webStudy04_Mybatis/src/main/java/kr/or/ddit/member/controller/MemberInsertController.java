package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
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

@CommandHandler
public class MemberInsertController{
	@URIMapping(value="/member/memberInsert.do", method=HttpMethod.GET)
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String view = "member/memberForm";
//		RequestDispatcher rd = req.getRequestDispatcher(view);
//		rd.forward(req, resp);
		return view;
	}
	
	@URIMapping(value="/member/memberInsert.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberVO member = new MemberVO(); //command Object
		req.setAttribute("member", member);
		try {
			BeanUtils.populate(member, req.getParameterMap());//파라키터맵의 사이즈는 0
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
		
		
		String goPage = null;
		String message = null;
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		GeneralValidator validator = new GeneralValidator();
		boolean valid = validator.validate(member, errors, InsertGroup.class);

		if (valid) {
			if(req instanceof FileUploadRequestWrapper) {//현재 요청 객체가 Wrapper인지 확인
				FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("mem_image");
				if(fileItem!=null) {
					member.setMem_img(fileItem.get());
				}
			}
			
			
			IMemberService service = new MemberServiceImpl();
			ServiceResult result = service.registMember(member);
			switch (result) {
			case PKDUPLICATED:
				goPage = "member/memberForm";
				message = "아이디 중복이니까 바꾸세요.";
				break;
			case FAILED:
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
	}
	
	
}
