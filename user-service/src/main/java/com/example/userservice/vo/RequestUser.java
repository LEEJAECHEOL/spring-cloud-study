package com.example.userservice.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestUser {
  @NotNull(message = "Email cannot be nulll")
  @Size(min = 2, message = "Email not be less than two characters")
  @Email
  private String email;

  @NotNull(message = "Name cannot be nulll")
  @Size(min = 2, message = "Name not be less than two characters")
  private String name;

  @NotNull(message = "Password cannot be nulll")
  @Size(min = 8, message = "Password must be equal or grater 8 two characters")
  private String pwd;
}
