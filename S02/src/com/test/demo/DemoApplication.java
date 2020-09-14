package com.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
////  	return args -> {
////  		String[] beanNames = ctx.getBeanDefinitionNames();
////  		Arrays.sort(beanNames);
////  		for(String beanName: beanNames)
////  		{
////  			System.out.println(beanName);
////  		}
////  	};
//
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//				String[] beanNames = ctx.getBeanDefinitionNames();
//				Arrays.sort(beanNames);
//				for (String beanName : beanNames) {
//					// if (beanName.startsWith("com.my"))
//					{
//						System.out.println(beanName);
//					}
//				}
//			}
//		};
//	}
}