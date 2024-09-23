package com.brijframework.authorization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationApplication {

	public static void main(String[] args) {
		//System.setProperty("spring.devtools.restart.enabled", "false");
		//SpringApplication.run(AuthorizationApplication.class, args);
		
		String str = "abccdeefgghi";

		Set<String> values=new HashSet<>();
		List<String> list=new ArrayList<>();

		char[] charArray=str.toCharArray();
		StringBuilder data=new StringBuilder("");
		for( Character ch: charArray){
		    values.add(ch.toString());
		    if(!values.contains(ch.toString())){
		       data.append(""+ch);
		    } else{
		      list.add(data.toString());
		      data=new StringBuilder(""+ch);
		   }
		}
		System.out.println(values);
		System.out.println(list);
	}

}
