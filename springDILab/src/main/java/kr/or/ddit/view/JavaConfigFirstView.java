package kr.or.ddit.view;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.ddit.conf.JavaConfigFirstContext;
import kr.or.ddit.service.ISampleService;

public class JavaConfigFirstView {
	public static void main(String[] args) {
		ConfigurableApplicationContext container =
				new AnnotationConfigApplicationContext(JavaConfigFirstContext.class);
		
		ISampleService service1 = container.getBean(ISampleService.class);
		ISampleService service2 = container.getBean(ISampleService.class);
		ISampleService service3 = container.getBean(ISampleService.class);
		
		System.out.println(service1.retrieveSampleContent("a001"));
		
		container.close();
	}
}
