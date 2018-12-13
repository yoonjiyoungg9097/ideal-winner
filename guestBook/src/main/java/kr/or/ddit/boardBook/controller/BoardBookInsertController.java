package kr.or.ddit.boardBook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.boardBook.service.BoardBookServiceImpl;
import kr.or.ddit.boardBook.service.IBoardBookService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.BoardBookVO;

@CommandHandler
public class BoardBookInsertController {
	IBoardBookService service = new BoardBookServiceImpl();
	
	@URIMapping(value="/boardBook/boardBookInsert.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BoardBookVO boardBook = new BoardBookVO();
		try {
			BeanUtils.populate(boardBook, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
		Map<String, String>errors = new HashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = validate(boardBook,errors);
		ServiceResult result = service.createBoardBook(boardBook);
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			ObjectMapper mapper = new ObjectMapper();
			resp.setContentType("application/json;charset=UTF-8");
			try(
				PrintWriter out = resp.getWriter(); 
			){
				mapper.writeValue(out, boardBook);
			}
			return null;
		}else {
			req.setAttribute("boardBook", boardBook);
			return "boardbook/boardbook";
		}
		
//		String view = null;
//		
//		if(valid) {
			
//			switch (result) {
//			case OK:
//				view = "redirect:/boardBook/boardBookList";
//				break;
//				
//			default:
//				req.setAttribute("message", "서버 오류이니 잠시 후 다시 시도해주세요");
//				view = "boardBook/boardBookList";
//				break;
//			}
//		}
//		System.out.println(view);
//		return view;
		
	}

	private boolean validate(BoardBookVO boardBook, Map<String, String> errors) {
		boolean valid = true;
		if(StringUtils.isBlank(boardBook.getBo_writer())) {
			errors.put("bo_writer", "작성자 누락");
			valid = false;
		}
		if(StringUtils.isBlank(boardBook.getBo_content())) {
			errors.put("bo_content", "내용 누락");
		}
		return valid;
	}
}
