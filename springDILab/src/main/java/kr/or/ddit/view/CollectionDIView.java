package kr.or.ddit.view;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import kr.or.ddit.vo.CollectionDIVO;

public class CollectionDIView {
	public static void main(String[] args) {
		ConfigurableApplicationContext container =
				new GenericXmlApplicationContext("classpath:kr/or/ddit/conf/collectionDIContainer.xml");
		
		CollectionDIVO collectionDIVO1 = container.getBean("collectionDIVO1", CollectionDIVO.class);
		CollectionDIVO collectionDIVO2 = container.getBean("collectionDIVO2", CollectionDIVO.class);
		System.out.println(collectionDIVO1==collectionDIVO2);
		System.out.println(collectionDIVO1);
		System.out.println(collectionDIVO2);
	}
}
