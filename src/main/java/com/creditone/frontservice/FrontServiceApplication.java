package com.creditone.frontservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
public class FrontServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontServiceApplication.class, args);
	}

}
