package kr.or.ddit.view;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import kr.or.ddit.vo.VariousDIVO;

public class VariousDIView {
	public static void main(String[] args) {
		ConfigurableApplicationContext container =
				new GenericXmlApplicationContext("classpath:kr/or/ddit/conf/variousDIContainer.xml");
		VariousDIVO diVO1 = container.getBean("variousDIVO1", VariousDIVO.class);
		VariousDIVO diVO2  = container.getBean("variousDIVO2", VariousDIVO.class);
		System.out.println(diVO1==diVO2);
		System.out.println(diVO1.getSampleFile().isDirectory());
		
	}
}
