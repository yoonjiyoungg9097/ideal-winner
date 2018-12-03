package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontController extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, ICommandHandler> handlerMap;
	private String mappingInfo;
	@Override
	public void init(ServletConfig config) throws ServletException {//fromtController가 실행되자마자 실행되는 부분
		super.init(config);
		handlerMap = new HashMap<>();
		mappingInfo = config.getInitParameter("mappingInfo");
		ResourceBundle bundle = ResourceBundle.getBundle(mappingInfo);
		Set<String> keySet = bundle.keySet();
		for(String uri:keySet) {
			String qualifiedName =bundle.getString(uri);//클래스 이름으로 로딩?
			try {
				Class<ICommandHandler> handlerClz = (Class<ICommandHandler>) Class.forName(qualifiedName.trim());
				ICommandHandler handler = handlerClz.newInstance(); //객체생성이 가능 리플렉션reflaction??
				handlerMap.put(uri.trim(), handler);
				logger.info(" {}에 대한 핸들러 {} 등록", uri, qualifiedName);//message argument {}=%s
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//				System.err.printf("%s 에 대한 핸들러 : %s 에서 문제 발생 %s\n", uri, qualifiedName, e.getMessage());
				logger.error("{} 에 대한 핸들러 : {} 에서 문제 발생 {}\n", uri, qualifiedName, e.getMessage());
				continue;
			} 
		}
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		1. 요청 매핑 설정 - web.xml에 servlet추가해주기
//		2. 요청 분석(주소,//여기서 부터 뒤에 있는 객체가 실행할 수 있다 파라미터, 메소드, 헤더...)
		String uri = req.getRequestURI(); //주소 분석
//		/webStudy03_Maven/memberList.do -> /memberList.do;session = 뒤에 session=빼주는 split 
		int cpLength = req.getContextPath().length();
		uri = uri.substring(cpLength).split(";")[0];
		System.out.println(uri);
		ICommandHandler handler = handlerMap.get(uri);
//		if("/member/memberList.do".equals(uri)) {
//			handler = new MemberListController();
//		}else if("/member/memberView.do".equals(uri)) {
//			handler = new MemberViewController();
//		}else if("/member/member/mypage.do".equals(uri)) {
//			handler = new MyPageController();
//		}else if("/member/member/memberUpdate.do".equals(uri)) {
//			handler = new MemberUpdateController();
//		}
		
		if(handler==null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "해당 서비스는 제공하지 않습니다.");
			return;
		}
//		3. 의존관계 형성 - Businness Logic Layer
//		4. 로직 선택
//		5. 모델 확보
//		6. 뷰 선택 //어떤 handler 객체인지 어떤 메소드인지
//		7. 모델 공유 //여기까지 뒤에있는 command handler가 처리
//		8. 뷰로 이동
		String view = handler.process(req, resp);
		String prefix = "/WEB-INF/views/";
		String suffix = ".jsp";
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
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "커맨드 헨들러에서 뷰가 선택되지 않았습니다.");
			}
		}
		
	}
}
