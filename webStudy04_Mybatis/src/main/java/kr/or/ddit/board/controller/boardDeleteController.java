package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BoardVO;

public class boardDeleteController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bo_noStr = req.getParameter("bo_no");
		long bo_no = 0;
		if(StringUtils.isNumeric(bo_noStr)) {
			bo_no = Long.parseLong(bo_noStr);
		}
		
		BoardVO board = new BoardVO();
		IBoardService service = new BoardServiceImpl();
		ServiceResult result = service.removeBoard(board);
		String page  = null;
		switch (result) {
		case OK:
			page = "redirect:/board/boardList.do";
			break;
		case FAILED:
			
			break;

		}
		return null;
	}

}
