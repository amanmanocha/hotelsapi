package com.agoda.hotels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.MappedInterceptor;

@SpringBootApplication
public class HotelsApplication {
  private final HandlerInterceptor rateLimitInterceptor;
  
  @Autowired
  public HotelsApplication(HandlerInterceptor interceptor) {
    this.rateLimitInterceptor = interceptor;
  }

  public static void main(String[] args) {
    SpringApplication.run(HotelsApplication.class, args);
  }
  
  @Bean
  public MappedInterceptor interceptors() {
    return new MappedInterceptor(new String[] { "/**" }, rateLimitInterceptor);
  }
}