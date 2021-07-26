package com.example.apiatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

  public CustomFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    // Custom Pre Filter
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.info("Custom PRE Filter: request id -> {}", request.getId());

      // Custom Post Filter
      // Mono : 웹 플럭스에서 지원함. 비동기 방식에서 사용
      // 내가 reactor 공부한거 참고!! 깃에 있음!
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        log.info("Custom POST Filter: response code -> {}", response.getStatusCode());
      }));
    };
  }

  public static class Config{
    // config 정보를 집어 넣을 수 있음.
  }

}
