package kr.or.ddit.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import kr.or.ddit.dao.ISampleDAO;
import kr.or.ddit.dao.SampleMySqlDAO;
import kr.or.ddit.dao.SampleOracleDAO;
import kr.or.ddit.service.ISampleService;
import kr.or.ddit.service.SampleService;

/**
 * Spring 4.1 버전 부터 xml을 이용하지 않고,
 * 자바 객체 형태로 BeanFactory/ApplicationContext에
 * bean metadata를 등록할 수 있는 java configuration 방식이 지원된다
 */
@Configuration
@Lazy
public class JavaConfigFirstContext {
	Logger logger = LoggerFactory.getLogger(JavaConfigFirstContext.class);
	
	@Bean("oracleDAO")
	public ISampleDAO getOracleDAO(){
		return new SampleOracleDAO();
	}
	
	@Bean
	public ISampleDAO mysqlDAO(){
		return new SampleMySqlDAO();
	}
	
	@Bean
	@Scope("prototype")//매번 주입할때마다 객체 생성을 하겠다
	public ISampleService sampleService(){
//		return new SampleService(mysqlDAO());
		logger.info("sampleService()를 호출했음");
		ISampleService service = new SampleService();
		service.setSampleDAO(getOracleDAO());
		return service;
	}
}
