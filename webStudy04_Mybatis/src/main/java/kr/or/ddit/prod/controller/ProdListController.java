package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.prod.dao.IOtherDAO;
import kr.or.ddit.prod.dao.OtherDAOImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ProdVO;

public class ProdListController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//제일 먼저 시작하는 페이지는 1이므로 currentPage=1
		int currentPage = 1;
		//만약 선택한 페이지가 있을때는 그 선택한 페이지를 요청받는다
		ProdVO searchVO = new ProdVO();
		searchVO.setProd_lgu(req.getParameter("prod_lgu"));
		searchVO.setProd_buyer(req.getParameter("prod_buyer"));
		searchVO.setProd_name(req.getParameter("prod_name"));
		
		String page = req.getParameter("page");
		if(StringUtils.isNumeric(page)) {
			currentPage = Integer.parseInt(page);
		}
		//모든 record수를 계산해주는 totalRecord로 그 값을 받아와 전체 페이지수를 정해준다
		PagingInfoVO<ProdVO> pagingVO = new PagingInfoVO<>(7,4);
		pagingVO.setSearchVO(searchVO);
		//currentpage를 파라미터로 받는 paginvo에 그 값을 전달해준다
		pagingVO.setCurrentPage(currentPage);
		
		IProdService service = new ProdServiceImpl();
		IOtherDAO otherDAO = new OtherDAOImpl();
		List<Map<String, Object>>lprodList = otherDAO.selectLprodList();
		List<BuyerVO>buyerList = otherDAO.selectBuyerList(null);
		req.setAttribute("lprodList", lprodList);
		req.setAttribute("buyerList", buyerList);
		long totalRecord = service.retrieveProdCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		
		//서비스로 페이징vo를 보내주어 그 값을 List<prodVO>타입으로 받아온다
		List<ProdVO>prodList = service.retrieveProdList(pagingVO);
		
		
		//request 스코프 영역에 pagingVO로 담아준다
//		req.setAttribute("prodList", prodList);
		pagingVO.setDataList(prodList);
		
		//Accept 헤더를 통해 동기/비동기 요청 여부를 확인하고,
		String accept = req.getHeader("Accept");//동기 요청인지 비동기 요청인지 확인
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			//비동기요청이라면, PagingVO를 marshalling한 JSON응답이 전송되도록
			//JSON
			resp.setContentType("application/json;charset=UTF-8");//마임을 json으로 설정
			ObjectMapper mapper = new ObjectMapper(); //마샬링(java->json)
			try (
					PrintWriter out = resp.getWriter();
			){
				mapper.writeValue(out, pagingVO); //마샬링과 직렬화를 한꺼번에 해주는 부분
			}
			return null;
		}else {
			//동기요청이라면, View Layer를 통해 응답이 전송
			//HTML
			req.setAttribute("pagingVO", pagingVO);
			return "prod/prodList";
		}
		
		
		
		
//		if(req.getParameter("page")!=null) {//null검증은 하지 않아도 isNumeric메서드 안에 값이 null일때 false
//			if(StringUtils.isNumeric(req.getParameter("page"))) {
//				currentPage = Integer.parseInt(req.getParameter("page"));
//			}
//		}
	}
}
