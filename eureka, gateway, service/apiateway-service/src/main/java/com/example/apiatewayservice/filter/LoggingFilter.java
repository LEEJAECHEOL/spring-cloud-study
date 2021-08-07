package com.example.apiatewayservice.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

  public LoggingFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    /**
     * webflux에서
     * ServerWebExchange exchange 에 request, response가 있음
     * GatewayFilterChain chain은 다양한 필터 프리 필터,포스트 필터, 프리체인 등
     *
     * OrderedGatewayFilter를 생성하여 우선순위(Order) 설정 가능
     * Ordered.HIGHEST_PRECEDENCE : HIGHEST라서 가장 먼저 실행 가능
     * LOWEST_PRECEDENCE : HIGHEST_PRECEDENCE의 반대
     */
    GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.info("Logging Filter baseMessage: {}", config.getBaseMessage());


      if(config.isPreLogger()){
        log.info("Logging Filter Start: request id -> {}", request.getId());
      }

      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        if(config.isPostLogger()){
          log.info("Logging Filter End: response code -> {}", response.getStatusCode());
        }
      }));
    }, Ordered.HIGHEST_PRECEDENCE);

    return filter;
  }

  @Getter
  @Setter
  public static class Config{
    // config 정보를 집어 넣을 수 있음.
    private String baseMessage;
    private boolean preLogger;
    private boolean postLogger;
  }

}
