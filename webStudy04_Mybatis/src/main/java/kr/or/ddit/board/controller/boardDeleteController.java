package kr.or.ddit.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.BoardVO;

@CommandHandler
public class boardDeleteController{
	IBoardService service = new BoardServiceImpl();

	@URIMapping(value="/board/boardDelete.do",method=HttpMethod.POST )
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String bo_noStr = req.getParameter("bo_no");
		String bo_pass = req.getParameter("bo_pass");
		
		if(!StringUtils.isNumeric(bo_noStr) ||
				StringUtils.isBlank(bo_pass)) {
			resp.sendError(400);
			return null;
		}
		
		ServiceResult result = service.removeBoard(new BoardVO(Long.parseLong(bo_noStr), bo_pass));
		
		String viewName = null;
		HttpSession session = req.getSession();
		switch (result) {
		case OK:
			viewName = "redirect:/board/boardList.do";
			break;
		case INVALIDPASSWORD:
			viewName = "redirect:/board/boardView.do?what="+bo_noStr;
			session.setAttribute("message", "비번 오류");
			break;
		default:
			viewName = "redirect:/board/boardView.do?what="+bo_noStr;
			session.setAttribute("message", "서버 오류");
			break;
		}
		return viewName;
	}

}
