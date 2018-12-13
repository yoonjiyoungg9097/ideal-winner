package kr.or.ddit.boardBook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.boardBook.service.BoardBookServiceImpl;
import kr.or.ddit.boardBook.service.IBoardBookService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.BoardBookVO;
import kr.or.ddit.vo.PagingInfoVO;

@CommandHandler
public class BoardBookListController {
	IBoardBookService service = new BoardBookServiceImpl();
	
	@URIMapping(value="/boardBook/boardBookList.do", method=HttpMethod.GET)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("sdfsdf");
		PagingInfoVO<BoardBookVO> pagingVO = new PagingInfoVO<>();
		long currentPage = 1;
		String pageStr = req.getParameter("page");
		if(StringUtils.isNumeric(pageStr)) {
			currentPage = Long.parseLong(pageStr);
		}
		long boardBookCount = service.retrieveTotalRecord(pagingVO);
		pagingVO.setTotalRecord(boardBookCount);
		pagingVO.setCurrentPage(currentPage);
		List<BoardBookVO>boardBookList = service.retrieveBoardBookList(pagingVO);
		pagingVO.setDataList(boardBookList);
		
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			ObjectMapper mapper = new ObjectMapper();
			resp.setContentType("application/json;charset=UTF-8");
			try(
				PrintWriter out = resp.getWriter(); 
			){
				mapper.writeValue(out, pagingVO);
			}
			return null;
		}else {
			req.setAttribute("pagingVO", pagingVO);
			return "boardbook/boardbook";
		}
	}
	@URIMapping(value="/boardBook/boardBookList.do", method=HttpMethod.POST)
	public String post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		return process(req, resp);
	}
	
}
