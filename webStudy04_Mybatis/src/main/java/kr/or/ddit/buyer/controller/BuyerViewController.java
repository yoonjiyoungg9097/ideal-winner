package kr.or.ddit.buyer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BuyerVO;

public class BuyerViewController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//선택한 buyer의 id를 받아온다
		//받아온 아이디가 null인지 아닌지 검증해준다
		//null이 아닐때 B.L.L과의 의존관계 형성해준다
		//서비스를 통해 받아온 값을 buyerVO타입에 담아준다
		//이동방식은 주소값을 보내주기 위해서 dispatcher방식으로 해준다
		//request 스코프 영역에 buyerVO를 담아준다
		String buyer_id = req.getParameter("who");
		IBuyerService service = new BuyerServiceImpl();
		BuyerVO buyerVO = new BuyerVO();
		if(buyer_id!=null) {
			buyerVO = service.retrieveBuyer(buyer_id);
		}
		String view = "buyer/buyerView";
		req.setAttribute("buyer", buyerVO);
		return view;
	}

}
