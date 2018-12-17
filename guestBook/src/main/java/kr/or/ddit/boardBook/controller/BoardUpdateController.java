package kr.or.ddit.boardBook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Update;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.boardBook.service.BoardBookServiceImpl;
import kr.or.ddit.boardBook.service.IBoardBookService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.BoardBookVO;

@CommandHandler
public class BoardUpdateController {
	IBoardBookService service = new BoardBookServiceImpl();
	
	@URIMapping(value="/boardBook/boardUpdate.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("ㅇㅇㅇㅇㅇㅇ");
		BoardBookVO boardBook = new BoardBookVO();
		try {
			BeanUtils.populate(boardBook, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		GeneralValidator validator =new GeneralValidator();
		
		Map<String, List<CharSequence>> errors = new HashMap<>();
		validator.validate(boardBook, errors, Update.class);
		
		ServiceResult result = service.modifyBoardBook(boardBook);
		Map<String, String> message = new HashMap<>();
		switch (result) {
		case OK:
			message.put("flag", "true");
			break;
		case INVALIDPASSWORD:
			message.put("flag", "false");
			message.put("password", "비밀번호 확인");
			break;
		default:
			message.put("flag", "false");
			message.put("failed", "서버 오류");
			break;
		}
		
		String header = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(header, "json")) {
			ObjectMapper mapper = new ObjectMapper();
			
			try(
				PrintWriter out = resp.getWriter();
			) {
				mapper.writeValue(out, message);
			} catch (IOException e) {
				
			}
			
		}
		
		return null;
		
	}
}
