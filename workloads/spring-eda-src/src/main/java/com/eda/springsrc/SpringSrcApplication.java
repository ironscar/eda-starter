package com.eda.springsrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan("com.eda.springsrc.repository")
@SpringBootApplication
public class SpringSrcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSrcApplication.class, args);
	}

}
