package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.FrontController;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;

public class BuyerListController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//처음 들어온 페이지는 1 그래서 현재페이지는 1
		//--까먹은거-------
		//먼저 totalRecord를 db에서 가져온다
		//pagingVO에서 currentpage를 파라미터로 받아와서  sp,ep,sr,er를 셋팅해주기위해 currentpage를 넘겨준다
		//---------------
		//고객이 선택한 페이지에 따라 그 페이지에 해당하는 buyer를 보여주기 위해
		//고객이 선택한 현재페이지를 가져온다 (그 현재페이지에 해당하는 buyer를 다시 가져오기 위해서)
		//--까먹은거-----------
		//만약 파라미터로 받아온 currentpage가 null이 아닐때를 검증해준다
		//그리고 받아온 currentpage를 타입을 맞춰주기 위해서 parserInt로 타입을 맞춰준다
		//pagingVO에서 currentpage를 파라미터로 받아와서  sp,ep,sr,er를 셋팅해주기위해 currentpage를 넘겨준다
		
		
		//B.L.L과의 의존관계를 형성해서 service로 가서 거기에 해당하는 값을 List<BuyerVO>타입으로 받아온다
		//--까먹은거--------
		//다시 frontcontroller로 가서 그에 해당하는 이동방식에 따라 보내주기 위해 그곳으로 이동
		//frontcontroller로 가기전에 해당하는 주소값을 보내주기 위해 dispatcher로 이동하기 때문에
		//앞쪽에 redirect:를 붙여주지 않는다
		//request 스코프 영역에 파라미터로 pagingVO를 담아준다
		int currentPage = 1;
		IBuyerService service = new BuyerServiceImpl();
		long totalPage = service.selectTotalRecord();
		PagingInfoVO pagingVO = new PagingInfoVO();
		
		FrontController fc = new FrontController();
		String page = req.getParameter("page");
		if(StringUtils.isNumeric(page)) {//숫자일때만 true
			currentPage = Integer.parseInt(page);
		}
		pagingVO.setTotalRecord(totalPage);
		pagingVO.setCurrentPage(currentPage);
		
		List<BuyerVO>buyerList = service.retrieveBuyerList(pagingVO);
		
		String view = "buyer/buyerList";
		req.setAttribute("pagingVO", pagingVO);
		req.setAttribute("buyerList", buyerList);
		return view;
	}

}
