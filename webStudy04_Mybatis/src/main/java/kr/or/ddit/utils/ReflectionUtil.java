package kr.or.ddit.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReflectionUtil {
	public static List<Class<?>> getClassesAtBasePackage(String basePackage) {
		List<Class<?>> classList = new ArrayList<>();
		
		URL baseURL = Thread.currentThread().getContextClassLoader().getResource("/"+basePackage.replace(".", "/"));
		if(baseURL==null) return classList;
		
		File baseFolder = new File(baseURL.getFile());
		String[] classArray = baseFolder.list((dir, name)->{
			boolean flag = false;
			flag = name.endsWith(".class");
			return flag;
		});
		
		if(classArray!=null) {
			for(String classFileName : classArray) {
				int lastIndex = classFileName.lastIndexOf(".class");
				String qualifiedName = basePackage+"."+classFileName.substring(0,lastIndex);
				try {
					Class clz = Class.forName(qualifiedName); //class loading
					classList.add(clz);
//					FirstAnnotation annotation = (FirstAnnotation) clz.getAnnotation(FirstAnnotation.class);
				} catch (ClassNotFoundException e) {
					continue;
				}
			}
		}
		
		return classList;
	}
	
	
	public static List<Class<?>> getClassesAtBasePackages(String...basePackages){
		List<Class<?>> classList = new ArrayList<>();
		if(basePackages!=null) {
			for (String basePackage : basePackages) {
				classList.addAll(getClassesAtBasePackage(basePackage));
			}
		}
		return classList;
	}
	
	public static List<Class<?>> getClassesWithAnnotationAtBasePackages(Class<? extends Annotation>annotationType, String...basePackages){
		List<Class<?>> classList = getClassesAtBasePackages(basePackages);
		for(int idx  = classList.size()-1; idx>=0; idx--) {
			Class<?> temp = classList.get(idx);
			if(temp.getAnnotation(annotationType)==null)
				classList.remove(idx);
		}
		return classList;
	}
	
	public static List<Method> getMethodsAtClass(Class<?> targetClz, Class<?> returnType, Class<?>...parameterTypes){
		List<Method> methodList = new ArrayList<>();
		if(targetClz!=null) methodList.addAll(Arrays.asList(targetClz.getDeclaredMethods()));
		for(int idx=methodList.size()-1; idx>=0; idx--) {
			Method temp = methodList.get(idx);
			if(
			   (returnType!=null && !returnType.equals(temp.getReturnType())
					|| (parameterTypes!=null && !Arrays.deepEquals(parameterTypes, temp.getParameterTypes())))){
				methodList.remove(idx);
			}
					
		}
		return methodList;
	}
	
	public static List<Method> getMethodsWithAnnotationAtClass(Class<?> targetClz, Class<? extends Annotation>annotationType, Class<?> returnType, Class<?>...parameterTypes){
		List<Method> methodList = getMethodsAtClass(targetClz, returnType, parameterTypes);
		for(int idx=methodList.size()-1; idx>=0; idx--) {
			Method temp = methodList.get(idx);
			if(temp.getAnnotation(annotationType)==null)
				methodList.remove(idx);
		}
		return methodList;
	}
}

