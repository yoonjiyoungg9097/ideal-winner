package kr.or.ddit.board.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.URIMapping.HttpMethod;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ReplyVO;

@CommandHandler
public class ReplyInsertController{

	@URIMapping(value="/reply/replyInsert.do", method=HttpMethod.POST)
	public String process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ReplyVO reply = new ReplyVO();// command object
		Map<String, String> errors = new HashMap<>();
		req.setAttribute("reply", reply);
		req.setAttribute("errors", errors);

		try {//프로퍼티명 같으면 자동 매핑
			BeanUtils.populate(reply, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		String page = null;
		boolean valid = validate(reply, errors);
		if (valid) {
			IReplyService service = new ReplyServiceImpl();
			ServiceResult result = service.createReply(reply);

			switch (result) {
			case OK:
				page = "redirect:/reply/replyList.do?bo_no="+reply.getBo_no();
				break;
			case FAILED:
				errors.put("error", "true");
				errors.put("message", "서버오류");
				break;
			}
		} else {// 검증 실패
			errors.put("error", "true");
			errors.put("message", "검증실패 데이터 오류 확인하세요");
		}
		

		return page;
	}

	private boolean validate(ReplyVO reply, Map<String, String> errors) {
		boolean valid = true;

		if (StringUtils.isBlank(reply.getRep_writer())) {
			valid = false;
			errors.put("rep_writer", "댓글 작성자 누락");
		}
		if (StringUtils.isBlank(reply.getRep_pass())) {
			valid = false;
			errors.put("rep_pass", "비밀번호 누락");
		}
		if(reply.getRep_content()!=null && reply.getRep_content().length()>100) {
			valid = false;
			errors.put("rep_content", "내용의 길이는 100글자 미만");
		}

		return valid;
	}
}
