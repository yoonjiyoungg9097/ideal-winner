package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.vo.BuyerVO;

public class BuyerUpdateController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//모든정보를 받아와요
		BuyerVO buyer = new BuyerVO();
		//buyervo를 request영역에 buyer라는 파라미터명으로 담아준다
		String method = req.getMethod();
		//B.L.L과의 의존관계 형성
		IBuyerService service = new BuyerServiceImpl();
		
		IOtherDAO dao = new OtherDAOImpl();
		List<Map<String, Object>>lprodList = dao.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		
		if("get".equalsIgnoreCase(method)) {
			String buyer_id = req.getParameter("who");
			buyer = service.retrieveBuyer(buyer_id);
			req.setAttribute("buyer", buyer);
			return "buyer/buyerForm";
		}else if("post".equalsIgnoreCase(method)) {
			
		try {//req영역의 모든 파라미터를 가져와 buyerVO에 담아준다
			BeanUtils.populate(buyer, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
		
		//그 받은 정보를 service로 보내주어 ServiceResult 타입에 담아준다
		ServiceResult result = service.modifyBuyer(buyer);
		//그 serviceresult값에 따라 이동방식과 보여줄 페이지를 보내준다
		String page = null;
		Map<String, String>errors = new HashMap<>();
		req.setAttribute("errors", errors);
		switch (result) {
		case PKNOTFOUND:
			page="buyer/buyerList";
			errors.put("PKNOTFOUND", "존재하지 않는 바이어인데");
			break;
		case OK:
			page="redirect:/buyer/buyerView.do?who="+buyer.getBuyer_id();
			errors.put("OK","성공적 업데이트");
			break;
		case FAILED:
			page="buyer/buyerList";
			errors.put("FAILED","서버 오류로 인한 실패");
			break;
		}
		return page;
		}
		return null;
	}

}
