package kr.or.ddit.prod.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;

@CommandHandler
public class ProdViewController{
	IProdService service = new ProdServiceImpl();

	@URIMapping("/prod/prodView.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String prod_id = req.getParameter("what");
		if(StringUtils.isBlank(prod_id)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		ProdVO prodVO = new ProdVO();
		prodVO = service.retrieveProd(prod_id);
		
		req.setAttribute("prod", prodVO);
		return "prod/prodView";
	}

}
