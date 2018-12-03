package kr.or.ddit.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FirstLoopTag extends SimpleTagSupport {
	private int loopCount;
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}
	
	private String data;
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		StringBuffer printData = new StringBuffer();
		for(int count=1; count<=loopCount; count++) {
			printData.append(data);
		}
		getJspContext().getOut().println(printData);
	}
}












