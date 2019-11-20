package com.sandesh.springlinkedin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"application.properties", "profiles-${spring.profiles.active}.properties"}) //profiles-{someText} is loaded according to the value provided in SPEL
																								// SPEL -> Spring Expression Language
// @Import(value = AnotherSpringConfig.class)      // This will help autowire the bean from AnotherSpringConfig class to SpringConfig class
// @ComponentScan("com.sandesh.springlinkedin")
public class SpringConfig {
	
	@Value(value = "${greeting.text}")
	public String greeting;
	
	// Priority is given more to the environment variables than that listed in property file
	@Value(value = "${JAVA_HOME}")
	public String javaHome;
	
	@Value(value = "${profiles.saysomething}")
	public String profile;
	
	// Injecting non string resources
//	@Value("#{new Boolean(envirorment['spring.profiles.active']=='prod')}")  // Using environment variable   // This might not work whereas the below one works 
	@Value("#{new Boolean('${spring.profiles.active}'=='dev')}")  // Using property file    // This can also be used for reading from environment variables
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
	public InnerClass fromProd() {
		return new InnerClass("Production");
	}
	
	
	public class InnerClass {
		private String value;
		public InnerClass(String value) {
			this.value = value;
		}
		public void print() {
			System.out.println("Hello " + value + ", Profile: " + profile + ", and boolean flag: " + isDev);
		}
	}
}
