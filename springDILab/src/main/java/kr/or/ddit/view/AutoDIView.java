package kr.or.ddit.view;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import kr.or.ddit.service.ISampleService;

public class AutoDIView {
	public static void main(String[] args) {
		ConfigurableApplicationContext container = 
				new GenericXmlApplicationContext("classpath:kr/or/ddit/conf/autoDI.xml");
		ISampleService service = container.getBean(ISampleService.class);
		System.out.println(service.hashCode());
		System.out.println(service.retrieveSampleContent("a001"));
		
		container.close();
	}
}
