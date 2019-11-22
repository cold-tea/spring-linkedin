package com.sandesh.springlinkedin.component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// If the class does not contains no-args constructor, then it will throw while trying to instantiate by bean factory
// Most have at least one constructor which can be instantiated properly otherwise exception will be thrown
@Component
public class Student{
	
	// Getting log4j logger implementing slf4j
	// Bad practice doing this in model class
	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	private int rollNo;
	private String name;
	private Address address;

	// This won't be used for the instantiation of default bean as the constructor with Autowired annotation override it
	public Student() {
		this.rollNo = 100;
		this.name = "Sandesh";
	}

	public Student(int rollNo, String name) {
		this.rollNo = rollNo;
		this.name = name;
	}

	@Autowired // This constructor makes it possible to instantiate the bean other the program will throw exception if no-arg constructor is not defined
				// If no arg constructor is also defined then the default bean initialized will be instantiated using this constructor
	public Student(Address address) {
		this();
		this.address = address;
	}
	
	@PostConstruct
	private void stuInitialized() {
		logger.warn("Student bean constructed, Name: " + this.name + ", Object: " + this);
	}
	
	@PreDestroy
	private void stuDestroyed() {
		logger.warn("Student bean destroyed, Name : " + this.name + ", Object: " + this);
	}
	
	@Loggable
	public int getRollNo() {
		return rollNo;
	}
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	
	@Loggable
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + "]";
	}
}
