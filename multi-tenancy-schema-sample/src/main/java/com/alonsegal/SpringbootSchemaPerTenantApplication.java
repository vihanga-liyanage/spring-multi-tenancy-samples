package com.alonsegal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootSchemaPerTenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSchemaPerTenantApplication.class, args);
	}
}
