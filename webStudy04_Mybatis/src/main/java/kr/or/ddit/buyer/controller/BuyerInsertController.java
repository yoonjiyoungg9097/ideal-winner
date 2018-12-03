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
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.CommonException;
import kr.or.ddit.ServiceResult;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.vo.BuyerVO;

public class BuyerInsertController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//먼저 메소드가 어떤 방식인지 가져온다
		String method = req.getMethod();
		IOtherDAO dao = new OtherDAOImpl();
		List<Map<String, Object>>lprodList = dao.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		
		if("get".equalsIgnoreCase(method)) {
			//일반 회원이 물품등록 폼을 요청함,,,
			//근데 엘지유는 셀렉트 박스로 구성해줘야함,,
			return "buyer/buyerForm";
		}else if("post".equalsIgnoreCase(method)) {
		
		// buyerVO에 입력한 모든값을 담아주기 위해 선언해준다
		BuyerVO buyer = new BuyerVO();
		// request영역에 buyerVO를 담아준다
		req.setAttribute("buyer", buyer);

		// 담아줄때 방식은 parametermap으로 담아준다
		try {
			BeanUtils.populate(buyer, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new CommonException(e);
		}
		// B.L.L과의 의존관계 형성
		IBuyerService service = new BuyerServiceImpl();
		// serviceResult 값에 따라 보여주는 페이지와 이동방식을 정해준다
		ServiceResult result = service.registBuyer(buyer);
		Map<String, String>errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		String page = null;
		boolean res = validate(buyer, errors);
		if(res) {
			switch (result) {
			case OK:
				page="redirect:/buyer/buyerList.do";
				break;
			case FAILED:
				page="buyer/buyerForm";
				errors.put("FAILED", "서버오류로 인한 실패ㅜㅠ");
				break;

			}
		}

		return page;
		}
		return null;
	}

	private boolean validate(BuyerVO buyer, Map<String, String> errors) {
		boolean valid = true;
		if (StringUtils.isBlank(buyer.getBuyer_id())) {
			valid = false;
			errors.put("buyer_id", "판매자아이디누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_name())) {
			valid = false;
			errors.put("buyer_name", "판매처명누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_lgu())) {
			valid = false;
			errors.put("buyer_lgu", "엘지유누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_comtel())) {
			valid = false;
			errors.put("buyer_comtel", "전번누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_fax())) {
			valid = false;
			errors.put("buyer_fax", "팩스누락");
		}
		if (StringUtils.isBlank(buyer.getBuyer_mail())) {
			valid = false;
			errors.put("buyer_mail", "이멜누락");
		}
		return valid;
	}

}
