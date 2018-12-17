package kr.or.ddit.view;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.vo.DbInfoVO;

public class PropertiesDescView {
	public static void main(String[] args) {
		Properties systemProps = System.getProperties();
		for (Entry<Object, Object> entry : systemProps.entrySet()) {
			System.out.printf("%s : %s\n", entry.getKey(), entry.getValue());
		}
		Map<String, String>envMap = System.getenv();
		for (Entry<String, String> entry : envMap.entrySet()) {
			System.err.printf("%s : %s\n", entry.getKey(), entry.getValue());
		}
		
//		ConfigurableApplicationContext container =
//				new ClassPathXmlApplicationContext("kr/or/ddit/conf/propertiesDesc.xml");
//		
//		Properties dbInfo = container.getBean("dbInfo", Properties.class);
//		for (Entry<Object, Object> entry : dbInfo.entrySet()) {
//			System.out.printf("%s : %s \n", entry.getKey(), entry.getValue());
//		}
//		DbInfoVO infoVO1 = container.getBean("dbInfoVO1", DbInfoVO.class);
//		DbInfoVO infoVO2 = container.getBean("dbInfoVO2", DbInfoVO.class);
//		System.out.println(infoVO1);
//		System.out.println(infoVO2);
//		container.close();
	}
}
