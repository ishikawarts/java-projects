package com.ishikawarts;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.ishikawarts.dao"})
public class ChoseiPachimonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChoseiPachimonApplication.class, args);
	}

}
