package kr.or.ddit.boardBook.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.boardBook.service.BoardBookServiceImpl;
import kr.or.ddit.boardBook.service.IBoardBookService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.BoardBookVO;

@CommandHandler
public class BoardBookDeleteController {
	IBoardBookService service = new BoardBookServiceImpl();

	@URIMapping(value="/boardBook/boardBookDelete.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) {
		BoardBookVO boardBook = new BoardBookVO();
		try {
			BeanUtils.populate(boardBook, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		req.setAttribute("boardBook", boardBook);
		ServiceResult result = service.removeBoardBook(boardBook);
		
		String view = null;
		switch (result) {
		case OK:
			view = "/boardbook/boardbookList";
			break;
		case INVALIDPASSWORD:
			view="";
			break;
		default:
			view="";
			break;
		}
		return view;
	}
}
