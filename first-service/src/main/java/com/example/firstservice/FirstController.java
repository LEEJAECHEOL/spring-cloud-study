package com.example.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/first-service")
public class FirstController {
  Environment environment;

  @Autowired
  public FirstController(Environment environment) {
    this.environment = environment;
  }

  @GetMapping("/welcome")
  public String welcom(){
    return "Welcome to the First service";
  }

  @GetMapping("/message")
  public String message(@RequestHeader("first-request") String header){
    log.info(header);
    return "Hello World in First service";
  }

  @GetMapping("/check")
  public String check(HttpServletRequest request){
    log.info("Server port = {}", request.getServerPort());

    return String.format("Hi, there. there is a message from Fist Service on Port %s",
                            environment.getProperty("local.server.port"));
  }
}
