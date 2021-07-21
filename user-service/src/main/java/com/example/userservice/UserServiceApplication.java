package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

}

/**
 * 명령어로 서버 추가 실행
 *  - mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=9003'
 *
 * cmd
 *  - mvn clean (target 삭제)
 *  - mvn compile package (컴파일 후 패키지 생성)
 *  - 빌드 성공 후 jar 파일이 만들어짐
 *  - jar 실행 : java -jar -Dserver.port=9004 ./target/user-service-0.0.1-SNAPSHOT.jar
 */
