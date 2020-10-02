package com.my.app;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.my.app.dao.UserAccountDao;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
    
    @Autowired
    private DataSource datasource;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
//				String[] beanNames = ctx.getBeanDefinitionNames();
//				Arrays.sort(beanNames);
//				for (String beanName : beanNames) {
//					// if (beanName.startsWith("com.my"))
//					{
//						System.out.println(beanName);
//					}
//				}
				
				try(Connection conn = datasource.getConnection()) {
		            System.out.println(conn);
		        }
				
				System.out.println(jdbcTemplate);
				
			}
		};
	}
}
