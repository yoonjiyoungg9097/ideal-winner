package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.Arrays;
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

import kr.or.ddit.mvc.annotation.HandlerInvoker;
import kr.or.ddit.mvc.annotation.HandlerMapper;
import kr.or.ddit.mvc.annotation.URIMappingInfo;

public class FrontController extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private HandlerMapper handlerMapper;
	private HandlerInvoker handlerInvoker;
	private ViewProcessor viewProcessor;
	
	@Override
	public void init(ServletConfig config) throws ServletException {//fromtController가 실행되자마자 실행되는 부분
		super.init(config);
		String basePackage = config.getInitParameter("basePackage");//공백이 하나 이상이면
		logger.info("{}", basePackage);//message argument
		handlerMapper = new HandlerMapper(basePackage);
		handlerInvoker = new HandlerInvoker();
		viewProcessor = new ViewProcessor();
		viewProcessor.setPrefix(config.getInitParameter("prefix"));
		viewProcessor.setSuffix(config.getInitParameter("suffix"));
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		URIMappingInfo mappingInfo = handlerMapper.findCommandHandler(req);
		if(mappingInfo!=null) {
			String viewName = handlerInvoker.invokeHandler(mappingInfo, req, resp);
			viewProcessor.viewProcess(viewName, req, resp);
		}else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, req.getRequestURI());
		}
	}
}
