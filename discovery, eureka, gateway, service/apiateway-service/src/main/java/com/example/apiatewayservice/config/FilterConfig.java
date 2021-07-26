package com.example.apiatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

  /**
   * RouteLocator를 반환해야 application.yml에서 설정한 것처럼 라우터 역할을 함
   * RouteLocatorBuilder 인자로 받아 빌더를 라우트 설정 후 리턴
   *
   * 각각의 route path와 uri로 라우트 시키며 중간 filter를 거침
   * first-request 헤더에 first-request-header값을
   * first-response 헤더에 first-response-header값을 넣는다.
   */
//  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
    return builder.routes()
                  .route(r -> r.path("/first-service/**")
                    .filters(
                      f -> f.addRequestHeader("first-request", "first-request-header")
                            .addResponseHeader("first-response", "first-response-header")
                    )
                    .uri("http://localhost:8081/")
                  )
                  .route(r -> r.path("/second-service/**")
                    .filters(
                      f -> f.addRequestHeader("second-request", "second-request-header")
                        .addResponseHeader("second-response", "second-response-header")
                    )
                    .uri("http://localhost:8082/")
                  )
                  .build();
  }
}
