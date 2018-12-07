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

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.ICommandHandler;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.InsertGroup;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PdsVO;

public class BoardInsertController implements ICommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 1. V.L : board/boardForm 
		// 2. 게시글에 첨부파일이 최대 3건 (partname=bo_file)
		// 3. 첨부파일 저장 위치 : d:/boardFiles
		
		String page = null;
		String method = req.getMethod();
		if("get".equalsIgnoreCase(method)) {
			return "board/boardForm";
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
				if(req instanceof FileUploadRequestWrapper) {
					List<FileItem> fileItems = ((FileUploadRequestWrapper) req).getFileItems("bo_file");
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
		}else {
			resp.sendError(405);
			return null;
		}
	
	return page;
	}
}
