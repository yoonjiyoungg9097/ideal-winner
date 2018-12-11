package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.BoardVO;

@CommandHandler
public class incrementRcmdController {
	IBoardService service = new BoardServiceImpl();
	@URIMapping(value="/board/incrementRcmd.do", method=HttpMethod.POST)
	//우리 오늘 짠 아 프레임워크가 매핑을 해주려면 기본적으로 반환타입이 스트링이야되고 파라미터로 리퀘스트랑 리스폰즈를 받아야합니다<

	public String incrementRcmd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String bo_noStr = req.getParameter("bo_no");
		if (!StringUtils.isNumeric(bo_noStr)) {
			resp.sendError(400);
			return null;
		}
		
		long bo_no = Long.parseLong(bo_noStr);
		BoardVO board = new BoardVO();
		req.setAttribute("board", board);
		board = service.incrementRcmd(bo_no);
		
		//조회수를 가져와서 증가시켜주면?
		board.setBo_rcmd(board.getBo_rcmd()+1);
		//그값을 돌려주면?

		ObjectMapper mapper = new ObjectMapper();
		try (PrintWriter out = resp.getWriter();) {
			mapper.writeValue(out, board);
		}

		return null;
	}
}
