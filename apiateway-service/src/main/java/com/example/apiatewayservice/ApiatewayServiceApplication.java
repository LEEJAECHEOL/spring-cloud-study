package com.example.apiatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiatewayServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiatewayServiceApplication.class, args);
  }

  @Bean
  public HttpTraceRepository httpTraceRepository(){
    return new InMemoryHttpTraceRepository();
  }

}
