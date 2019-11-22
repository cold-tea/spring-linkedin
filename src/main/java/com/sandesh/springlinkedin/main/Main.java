package com.sandesh.springlinkedin.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.sandesh.springlinkedin.component.LoggingAspect;
import com.sandesh.springlinkedin.component.Student;
import com.sandesh.springlinkedin.config.SpringConfig;

public class Main {
	
	// Getting log4j logger implementing slf4j
	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	public static void main(String[] args) {
		try (AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfig.class);) {
			String greetText = appContext.getBean("greeting", String.class);
			logger.warn("Greeting text: " + greetText);
			logger.warn("-------------------");
			
			SpringConfig.InnerClass inner = appContext.getBean(SpringConfig.InnerClass.class);
			inner.print();
			
			logger.warn("----------- (STUDENT)");
			Student student = appContext.getBean("student", Student.class);
			logger.warn("Student -> Name: " + student.getName() + ", Roll: " + student.getRollNo());
		}
	}
}
