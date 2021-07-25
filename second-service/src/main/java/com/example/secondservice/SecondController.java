package com.example.secondservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SecondController {

  @GetMapping("/welcome")
  public String welcom(){
    return "Welcome to the Second service";
  }
}
