package kr.or.ddit.listener;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CunstomServletRequestListener implements ServletRequestListener {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public void requestInitialized(ServletRequestEvent sre)  {
		ServletRequest request = sre.getServletRequest();
		HttpServletRequest req = (HttpServletRequest)request;
		
		logger.info("{} 요청이 {}에 발생했음", req.getRequestURI(), new Date().toString());
	}

    public void requestDestroyed(ServletRequestEvent sre)  { 
    	ServletRequest request = sre.getServletRequest();
		HttpServletRequest req = (HttpServletRequest)request;
		
		logger.info("{} 요청이 {}에 소멸했음", req.getRequestURI(), new Date().toString());
    }

	
}
