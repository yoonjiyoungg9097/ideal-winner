package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BuyerVO;

public class BuyerDeleteController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//아이디를 요청받아온다
		String buyer_id = req.getParameter("buyer_id");
		String message = null;
		//아이디 null검증
		if(StringUtils.isBlank(buyer_id)) {
			message = "다시 시도해주세요";
		}
		System.out.println(buyer_id+"ㅇ만윔ㄴ윈윙ㅁ뉘ㅏ");
		//B.L.L 과의 의존관계 형성
		IBuyerService service = new BuyerServiceImpl();
		//service를 통해 받아오는 ServiceResult값을 가져온다
		ServiceResult result = service.removeBuyer(buyer_id);
		
		//보여줄 jsp를 값으로 반환하기 위해서 변수로 선언하고 각각의 조건문에서 값을 넣어줍니다
		String view = null;
		switch (result) {
		case OK:
			view = "redirect:/buyer/buyerView.do?who="+buyer_id;
			break;
		case PKNOTFOUND:
			message = "존재하지 않는 아이디 입니다ㅜㅠ";
			view = "buyer/buyerForm";
			break;
		case FAILED:
			message = "서버 오류로 인해 삭제 실패되었으니 다시 시도해주세요 ㅜㅠ";
			view = "buyer/buyerForm";
			break;
		}
		
//		if(result.equals("PKNOTFOUND")) {
//			message.put("PKNOTFOUND", "존재하지 않는 아이디 입니다ㅜㅠ");
//			view = "buyer.buyerForm";
//		}else if(result.equals("OK")) {
//			//OK일때 delete성공 메세지 dispatcher방식(돌려줄 값이 없기 때문에)
//			message.put("OK", "삭제성공이다!!");
//			view = "redirect:buyer.buyerForm";
//		}else if(result.equals("FAILED")) {
//			//FAILED일때 서버 오류 메세지 이때 request스코프 영역에 삭제에 실패한 아이디를 담아준다 redirect방식
//			message.put("FAILED", "서버 오류로 인해 삭제 실패되었으니 다시 시도해주세요 ㅜㅠ");
//			view = "buyer.buyerForm";
//		}
		
		//message를 request 스코프 영역에 message라는 파라미터명으로 담아주세요
		req.setAttribute("message", message);
		return view;
	}

}
