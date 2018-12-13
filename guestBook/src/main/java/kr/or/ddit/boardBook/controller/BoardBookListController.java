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
import kr.or.ddit.vo.BoardBookVO;
import kr.or.ddit.vo.PagingInfoVO;

@CommandHandler
public class BoardBookListController {
	IBoardBookService service = new BoardBookServiceImpl();
	
	@URIMapping("/boardBook/boardBookList.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PagingInfoVO<BoardBookVO> pagingVO = new PagingInfoVO<>();
		long currentPage = 1;
		String pageStr = req.getParameter("page");
		if(StringUtils.isNumeric(pageStr)) {
			currentPage = Long.parseLong(pageStr);
		}
		BoardBookVO boardBook = new BoardBookVO();
		long boardBookCount = service.retrieveTotalRecord(pagingVO);
		List<BoardBookVO>boardBookList = service.retrieveBoardBookList(pagingVO);
		pagingVO.setTotalPage(boardBookCount);
		pagingVO.setDataList(boardBookList);
		
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			ObjectMapper mapper = new ObjectMapper();
			try(
				PrintWriter out = resp.getWriter(); 
			){
				mapper.writeValue(out, pagingVO);
			}
			return null;
		}else {
			req.setAttribute("pagingVO", pagingVO);
			return "boardBook/boardBookList";
		}
	}
}
