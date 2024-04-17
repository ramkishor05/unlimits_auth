package com.brijframwork.authorization;

import org.brijframework.bean.builder.BeanBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableDiscoveryClient
@SpringBootApplication
//@EnableSwagger2
public class AuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
		
		BeanBuilder builder=new BeanBuilder(args);
		
		builder.set
	}

}
