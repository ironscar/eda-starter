package com.eda.springtgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan("com.eda.springtgt.repository")
@SpringBootApplication
public class SpringTgtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTgtApplication.class, args);
	}

}
