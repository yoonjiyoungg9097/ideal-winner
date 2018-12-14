package kr.or.ddit.board.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilterWrapper;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.validator.GeneralValidator;
import kr.or.ddit.validator.UpdateGroup;
import kr.or.ddit.vo.BoardVO;

@CommandHandler
public class boardUpdateController{
	IBoardService service = new BoardServiceImpl();

	@URIMapping("/board/boardUpdate.do")
	public String getProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String what = req.getParameter("what");
		if(!StringUtils.isNumeric(what)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		long bo_no = Long.parseLong(what);
		BoardVO board = new BoardVO(); //Command Object?
		board = service.retriveBoard(bo_no);
		req.setAttribute("board", board);
		return "board/boardForm";
	}
		
	@URIMapping(value="/board/boardUpdate.do", method=HttpMethod.POST)
	public String postProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {BoardVO board= new BoardVO();
    
    try {
       BeanUtils.populate(board, req.getParameterMap());
    } catch (IllegalAccessException | InvocationTargetException e) {
       e.printStackTrace();
    }
    Map<String, List<CharSequence>> errors=new HashMap<>();
    GeneralValidator validator=new GeneralValidator();
    boolean valid=validator.validate(board, errors, UpdateGroup.class);
    String view="board/boardForm";
    if(valid) {
    	HttpServletRequest request = req;
		if(req instanceof XssEscapeServletFilterWrapper) {
			request = (HttpServletRequest) ((XssEscapeServletFilterWrapper) req).getRequest();
		}
    	
       if(request instanceof FileUploadRequestWrapper) {
          List<FileItem> fileItems= ((FileUploadRequestWrapper) request).getFileItems("bo_file");
          board.setItemList(fileItems);
       }
       ServiceResult res= service.modifyBoard(board);
       switch (res) {
       case OK:
          //PRG패턴 포스트- 리다이렉트 - 겟요청 
          view="redirect:/board/boardView.do?what="+board.getBo_no();
          break;
       case FAILED:
          req.setAttribute("message", "서버오류랑");
          break;
       case INVALIDPASSWORD:
          req.setAttribute("message", "비번틀렸당");
          
          break;
       }
       
    }
    req.setAttribute("errors",errors );
    req.setAttribute("board", board);
	return view;
	}
}
