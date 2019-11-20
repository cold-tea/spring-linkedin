package com.sandesh.springlinkedin.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.sandesh.springlinkedin.config.SpringConfig;

public class Main {

	public static void main(String[] args) {
		try (AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfig.class);) {
			String greetText = appContext.getBean("greeting", String.class);
			System.out.println("Greeting text: " + greetText);
			System.out.println("-------------------");
			
			SpringConfig.InnerClass inner = appContext.getBean(SpringConfig.InnerClass.class);
			inner.print();
		}
	}

}
