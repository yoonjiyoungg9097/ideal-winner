package kr.or.ddit.boardBook.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public String process(HttpServletRequest req, HttpServletResponse resp) {
		PagingInfoVO<BoardBookVO> pagingVO = new PagingInfoVO<>();
		BoardBookVO boardBook = new BoardBookVO();
		List<BoardBookVO>boardBookList = service.retrieveBoardBookList(pagingVO);
		return null;
	}
}
