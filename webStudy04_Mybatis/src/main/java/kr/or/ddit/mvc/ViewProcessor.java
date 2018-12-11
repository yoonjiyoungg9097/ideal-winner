package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewProcessor {
	private String prefix;
	private String suffix;
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	
	/**
	 * 논리적인 view name을 받아서 해당 view layer 로 forwarding 이나 redirecting 을 하기위한 메소드
	 * @param viewName
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void viewProcess(String view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		if(view!=null) {
			boolean redirect = view.startsWith("redirect:");
			if(redirect) {
				view = view.substring("redirect:".length());
				resp.sendRedirect(req.getContextPath()+view);
			}else {
				RequestDispatcher rd = req.getRequestDispatcher(prefix+view+suffix);
				rd.forward(req, resp);
			}
		}else {
			if(!resp.isCommitted()) {//500
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "커맨드 핸들러에서 뷰가 선택되지 않았습니다.");
			}
		}
	}
}
