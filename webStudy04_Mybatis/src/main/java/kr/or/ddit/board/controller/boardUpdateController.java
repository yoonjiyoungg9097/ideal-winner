package kr.or.ddit.board.controller;

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

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.vo.BoardVO;

public class boardUpdateController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		1. V.L : "board/boardForm"
//		2. 기존 첨부파일 있다면, 삭제 가능하도록
//		3. 새로운 첨부파일이 있다면, 업로드
		
		String method = req.getMethod();
		IBoardService service = new BoardServiceImpl();
		BoardVO board = new BoardVO(); //Command Object?
		String Page = null;
		long bo_no = 0;
		if("get".equalsIgnoreCase(method)) {
			String what = req.getParameter("what");
			if(StringUtils.isNumeric(what)) {
				bo_no = Long.parseLong(what);
			}
			board = service.retriveBoard(bo_no);
			req.setAttribute("board", board);
			return "board/boardForm";
		}else if("post".equalsIgnoreCase(method)) {
			try {
				BeanUtils.populate(board, req.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			Map<String, List<CharSequence>>errors = new HashMap<>();
			ServiceResult result = service.modifyBoard(board);
			switch (result) {
			case OK:
				// POST-redirect-GET : PRG 패턴
				Page = "redirect:/board/boardView.do?what="+board.getBo_no();
				break;
			case INVALIDPASSWORD:
				Page = "board/boardView?what"+board.getBo_no();
				break;
			case FAILED:
				Page = "board/boardForm";
				break;

			}
		}
		return Page;
	}

}
