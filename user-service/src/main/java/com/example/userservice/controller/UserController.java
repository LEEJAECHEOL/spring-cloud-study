package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {
  private final Environment env;
  private final Greeting greeting;
  private final UserService userService;

  @GetMapping("/health_check")
  public String status(){
    return String.format("It's Working in User Service on Port %s", env.getProperty("local.server.port"));
  }
  @GetMapping("/welcome")
  public String welcome(){
//    return env.getProperty("greeting.message");
    return greeting.getMessage();
  }

  @PostMapping("/users")
  public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser){
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserDto userDto = mapper.map(requestUser, UserDto.class);
    userService.createUser(userDto);

    ResponseUser responseUser = mapper.map(userDto,ResponseUser.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
  }
}
