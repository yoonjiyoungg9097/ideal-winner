package kr.or.ddit.board.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilterWrapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PdsVO;

@CommandHandler
public class BoardInsertController {

	@URIMapping(value="/board/boardInsert.do", method=HttpMethod.GET)
	public String getProcess(HttpServletRequest req, HttpServletResponse resp) {
		return "board/boardForm";
	}
	@URIMapping(value="/board/boardInsert.do", method=HttpMethod.POST)
	public String postProcess(HttpServletRequest req, HttpServletResponse resp) {
		String page = null;
		String method = req.getMethod();
		if("get".equalsIgnoreCase(method)) {
		}else if("post".equalsIgnoreCase(method)) {
			BoardVO board = new BoardVO();
			req.setAttribute("board", board);
			try {
				BeanUtils.populate(board, req.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			GeneralValidator validator = new GeneralValidator();
			Map<String, List<CharSequence>>errors = new LinkedHashMap<>();
			req.setAttribute("errors", errors);
			boolean valid = validator.validate(board, errors, InsertGroup.class);
			if(valid) {
				HttpServletRequest request = req;
				if(req instanceof XssEscapeServletFilterWrapper) {
					request = (HttpServletRequest) ((XssEscapeServletFilterWrapper) req).getRequest();
				}
				
				if(request instanceof FileUploadRequestWrapper) {
					List<FileItem> fileItems = ((FileUploadRequestWrapper) request).getFileItems("bo_file");
					board.setItemList(fileItems);
				}
				
				IBoardService service = new BoardServiceImpl();
				ServiceResult result = service.createBoard(board);
				switch (result) {
				case OK:
					page = "redirect:/board/boardList.do";
					break;
				default:
					req.setAttribute("message", "서버 오류");
					page = "board/boardForm";
					break;
				}
			}else {
				page = "board/boardForm";
			}
		}
	
	return page;
	}
	

}
