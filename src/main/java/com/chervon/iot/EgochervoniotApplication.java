package com.chervon.iot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableAutoConfiguration
@MapperScan("com.chervon.iot.*.mapper")
public class EgochervoniotApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgochervoniotApplication.class, args);
	}

}
