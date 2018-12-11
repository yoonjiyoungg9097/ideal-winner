package kr.or.ddit.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.ReplyVO;

@CommandHandler
public class ReplyUpdateController{

	@URIMapping(value="/reply/replyUpdate.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String rep_noStr = req.getParameter("rep_no");
		String rep_pass = req.getParameter("rep_pass");
		String bo_noStr = req.getParameter("bo_no");
		
		if(!StringUtils.isNumeric(rep_noStr)||
				StringUtils.isBlank(rep_pass)||
				!StringUtils.isNumeric(bo_noStr)) {
			resp.sendError(400);
		}
		
		ReplyVO reply = new ReplyVO();
		req.setAttribute("reply", reply);
		
		Map<String, String> errors = new HashMap<>();
		IReplyService service = new ReplyServiceImpl();
		ServiceResult result = service.modifyReply(reply);
		String page = null;
		switch (result) {
		case OK:
			page = "redirect:/reply/replyList.do?bo_no="+reply.getBo_no()+"&page=1";
			break;
		case INVALIDPASSWORD:
			break;
		default:
			break;
		}
		return page;
	}

}
