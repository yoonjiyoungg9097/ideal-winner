package kr.or.ddit.view;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;

import kr.or.ddit.dao.ISampleDAO;
import kr.or.ddit.service.ISampleService;

public class DIContainerDescView {
	public static void main(String[] args) throws IOException {
//		ConfigurableApplicationContext container =
//				new ClassPathXmlApplicationContext("kr/or/ddit/conf/DIContainerDesc.xml");
		ConfigurableApplicationContext container =
				new GenericXmlApplicationContext("classpath:kr/or/ddit/conf/DIContainerDesc.xml");
		
//		prefix를 통해 리소스(Resource)를 검색하는 Smart ResourceLoader가 컨테이너 내부에 있음.
//		1. classpath : 클래스패스 리소스 로딩 (ClasspathResource)
//		2. file : 파일시스템 리소스 로딩 (FileSystemResource)
//		3. url : 웹리소스 로딩 (ServletContextResource)
		Resource resource = container.getResource("classpath:log4j2.xml");
		System.out.println(resource.getFile().getAbsolutePath());
		System.out.println(resource.getClass().getSimpleName());
		
		Resource fileSystemRes = container.getResource("file:D:/B_Util/2.Java/JDK1.7/README.html");
		System.out.println(fileSystemRes.exists());
		System.out.println(fileSystemRes.getClass().getSimpleName());
		
//		ISampleService service1 = container.getBean("sampleService1", ISampleService.class);
//		ISampleService service2 = container.getBean("sampleService2", ISampleService.class);
//		System.out.println(service1.retrieveSampleContent("a001"));
//		System.out.println(service1==service2);//singleton??
//		
//		ISampleDAO sampleDAO1 = container.getBean("sampleOracleDAO", ISampleDAO.class);
//		ISampleDAO sampleDAO2 = container.getBean("sampleOracleDAO", ISampleDAO.class);
//		ISampleDAO sampleDAO3 = container.getBean("sampleOracleDAO", ISampleDAO.class);
//		
//		System.out.println(sampleDAO1.hashCode());
//		System.out.println(sampleDAO2.hashCode());
//		System.out.println(sampleDAO3.hashCode());
		container.close();
	}
}
