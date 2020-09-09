package com.my.dcm;

import com.my.filter.MyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.my"})
public class DcmService01 implements ApplicationRunner{

	
	private static final Logger logger = LoggerFactory.getLogger(DcmService01.class);
	
    public static void main(String[] args) {
    	logger.info("BEFORE SpringApplication.run");
    	
        SpringApplication.run(DcmService01.class, args);
        
        logger.info("AFTER SpringApplication.run");

        String applicationPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(applicationPath);
    }

    @GetMapping("/dcm")
    public String hello(@RequestParam(value = "name", defaultValue = "DICOM") String name) {
        return String.format("Hello %s!", name);
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("********** DcmService01::run");
	}

    @Bean
    public MyFilter newMyFilter() {
        return new MyFilter();
    }
    
//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
////    	return args -> {
////    		String[] beanNames = ctx.getBeanDefinitionNames();
////    		Arrays.sort(beanNames);
////    		for(String beanName: beanNames)
////    		{
////    			System.out.println(beanName);
////    		}
////    	};
//    	
//    	return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//	    		String[] beanNames = ctx.getBeanDefinitionNames();
//	    		// Arrays.sort(beanNames);
//	    		for(String beanName: beanNames)
//	    		{
//	    			System.out.println(beanName);
//	    		}
//			}
//		};
//    }
}
