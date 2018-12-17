package kr.or.ddit.view;

import java.util.Arrays;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.ddit.conf.JavaConfigAutoDI;

public class JavaConfigAutoDIView {
	public static void main(String[] args) {
		ConfigurableApplicationContext container =
				new AnnotationConfigApplicationContext(JavaConfigAutoDI.class);
		
		int beanCount = container.getBeanDefinitionCount();
		String[] beanNames = container.getBeanDefinitionNames();
		System.out.printf("%d 개의 빈이 등록되었고, 해당 빈들의 이름은 %s\n", beanCount, Arrays.toString(beanNames));
		
		container.close();
	}
}
