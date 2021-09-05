package com.example.userservice.security;


import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private Environment env;

  public WebSecurity(Environment env,UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder)    {
    this.env = env;
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
//    http.authorizeRequests().antMatchers("/users/**").permitAll();

    http.authorizeRequests().antMatchers("/actuator/**").permitAll();
    http.authorizeRequests().antMatchers("/health_check/**").permitAll();
    http.authorizeRequests().antMatchers("/**")
      .hasIpAddress("192.168.0.4")
      .and()
      .addFilter(getAuthenticationFilter());

    // h2-console 같은 html이 frame으로 구성되어 있는 화면을 사용 가능하도록 disalbe() 해줌.
    http.headers().frameOptions().disable();
  }

  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    AuthenticationFilter authenticationFilter =
      new AuthenticationFilter(authenticationManager(), userService, env);
    authenticationFilter.setAuthenticationManager(authenticationManager()); //스프링 시큐리티에서 기본으로 제공하는 매니저 사용

    return authenticationFilter;
  }

  //select pwd from users where email = ?
  //db_pwd(encrypted) == input_pwd(encrypted)
  //인증 관련
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }

}
