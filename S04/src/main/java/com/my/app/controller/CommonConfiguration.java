//package com.my.app.controller;
//
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//
//@Configuration
//public class CommonConfiguration {
//    @Bean
//    public EmbeddedServletContainerFactory embeddedServletContainerFactory(){
//        ConfigurableEmbeddedServletContainer factory = new TomcatEmbeddedServletContainerFactory();
//        factory.setDocumentRoot(new File("E:\\WorkSpace\\open-platform\\open-console\\src\\main\\webapp\\"));
//        return (EmbeddedServletContainerFactory) factory;
//    }
//}