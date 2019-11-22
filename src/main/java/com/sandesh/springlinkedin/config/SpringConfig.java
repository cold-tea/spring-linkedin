package com.sandesh.springlinkedin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.sandesh.springlinkedin.component.LoggingAspect;
import com.sandesh.springlinkedin.component.Student;

@Configuration
@PropertySource(value = {"application.properties", "profiles-${spring.profiles.active}.properties"}) //profiles-{someText} is loaded according to the value provided in SPEL
																								// SPEL -> Spring Expression Language
// @Import(value = AnotherSpringConfig.class)      // This will help autowire the bean from AnotherSpringConfig class to SpringConfig class
@ComponentScan("com.sandesh.springlinkedin.component")  // This is an annotation based configuration used in spring
@EnableAspectJAutoProxy
public class SpringConfig {
	
	// Getting log4j logger implementing slf4j
	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Value(value = "${greeting.text}")
	public String greeting;
	
	// Priority is given more to the environment variables than that listed in property file
	// The value is taken from environment variable and this is not listed in property file
	@Value(value = "${JAVA_HOME}")
	public String javaHome;
	
	@Value(value = "${profiles.saysomething}")
	public String profile;
	
	// Injecting non string resources (Both annotations are same, below one preferrable)
//	@Value("#{new Boolean(environment['spring.profiles.active']=='dev')}")  // Using environment variable and  property file 
//	@Value("#{new Boolean('${spring.profiles.active}'=='dev')}")  // Using environment variable and  property file  
	private boolean isDev;

	@Bean
	public String greeting() {
		return "Namaste " + greeting + ", " + javaHome;
	}
	
	// Reads spring.profiles.active property set in the env variables/property file and check whether the value is dev or prod and display result accordingly
	// In our case it is set in the property file named application.properties
	@Bean
	@Profile({"dev"})
	public InnerClass fromDev() {
		return new InnerClass("Development");
	}
	
	@Bean
	@Profile("prod")
	// @Prototype - not a shared instance like in singleton / @Session - used only in web, one instance per user session / @Request - used only in web, one instance per request
	public InnerClass fromProd() {
		return new InnerClass("Production");
	}
	
	@Bean
	public Student getStudent() {
		return new Student(200, "Mahesh");
	}
	
	
	public class InnerClass {
		private String value;
		public InnerClass(String value) {
			this.value = value;
		}
		public void print() {
			logger.warn("Hello " + value + ", Profile: " + profile + ", and boolean flag: " + isDev);
		}
	}
}
