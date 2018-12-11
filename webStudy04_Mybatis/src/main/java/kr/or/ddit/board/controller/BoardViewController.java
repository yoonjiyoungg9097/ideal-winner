package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingInfoVO;
import kr.or.ddit.vo.ReplyVO;

@CommandHandler
public class BoardViewController {

	@URIMapping("/board/boardView.do")
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String what = req.getParameter("what");
		long bo_no = 0;
		if(StringUtils.isNumeric(what)) {
			bo_no = Long.parseLong(what);
		}
		IBoardService service = new BoardServiceImpl();
		BoardVO board = service.retriveBoard(bo_no);
		
		req.setAttribute("board", board);
		return "board/boardView";
	}

}
