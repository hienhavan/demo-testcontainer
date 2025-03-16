package com.example.demo_testcontainer;

import org.springframework.boot.SpringApplication;

public class TestDemoTestcontainerApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoTestcontainerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
