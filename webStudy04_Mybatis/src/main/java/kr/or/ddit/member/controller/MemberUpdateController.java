package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberUpdateController {
	@URIMapping(value="/member/memberUpdate.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//입력한 값에 특수문자등을 처리해주기 위해서 인코딩해주는 부분
//		req.setCharacterEncoding("UTF-8");                                                            
		//memberView.jsp에서 입력한 값을 담은 VO - 이 VO에서 파라미터 값들을 가져오기 위해서 선언해준다
		MemberVO member = new MemberVO();
		
		try {
			//memberVO의 파라미터를 맵형식으로 가져올수 있다
			BeanUtils.populate(member, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			//그 값이 존재하지 않을때 CommonException
			throw new CommonException(e);
		}
		//request scope영역에 파라미터명을 "member" 키값으로 memberVO객체를 담아준다
		//성공했을때는 값을 보내지 않고 응답을 해주는 redirect이지만
		//실패했을때는 기존의 데이터를 가지고 가야하기 때문에 requst 스코프영역에 기존의 값을 담기위한 객체를 담아준다
		req.setAttribute("member", member);
		
		
		String goPage = null;
//		boolean redirect = false;
		String message = null;
		//에러메세지를 띄어주기 위해서 errors map을 선언해준다
		Map<String, List<CharSequence>> errors = new LinkedHashMap<>();
		//request 스코프영역에 파라미터명을 "errors" 키값으로 errors맵을 담아준다
		req.setAttribute("errors", errors);
		//validate메서드를 불러와서 valid라는 변수에 담아준다
		GeneralValidator validator = new GeneralValidator();
		boolean valid = validator.validate(member, errors, UpdateGroup.class);
		
		if(valid) {
			
			if(req instanceof FileUploadRequestWrapper) {
				System.out.println("아니 1");
				FileItem fileItem = ((FileUploadRequestWrapper) req).getFileItem("mem_image");
				if(fileItem!=null) {
					System.out.println("아니 똥");
					member.setMem_img(fileItem.get());
				}else {
					
				}
			}
			//Businness Logic Layer와의 의존관계 형성
			IMemberService service = new MemberServiceImpl();
			//service의 회원수정 메서드를 불러와 값을 ServiceResult타입에 담아준다
			ServiceResult result = service.modifyMember(member);
			switch(result) {
			case INVALIDPASSWORD : 
				goPage = "member/memberView";
				message = "일치하는 비밀번호가 없습니다";
				break;
			case FAILED :
				goPage = "member/memberView";
				message = "서버 오류로 인한 실패, 잠시 뒤 다시 해주세요";
				break;
			case OK:
//				goPage = "/member/memberView.do?who="+member.getMem_id();
				goPage = "redirect:/member/mypage.do";
//				redirect = true;
				break;
			}
			req.setAttribute("message", message);
		}else {
			goPage = "member/memberView";
		}
		
//		if (redirect) {//성공했을때 기존의 데이터를 담아주지 않아도 되기때문에 redirect
//			resp.sendRedirect(req.getContextPath() + goPage);
//		} else {
//			RequestDispatcher rd = req.getRequestDispatcher(goPage);
//			rd.forward(req, resp);
//		}
		return goPage;
		
	}
	
}
