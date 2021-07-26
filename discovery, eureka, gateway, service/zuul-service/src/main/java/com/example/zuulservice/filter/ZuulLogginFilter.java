package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;


@Component
@Slf4j
public class ZuulLogginFilter extends ZuulFilter {

  // 사전 필터인지 사후 필터인지 정의
  @Override
  public String filterType() {
    return "pre"; // pre 사전필터
  }

  // 여러개의 필터가 있을경우 필터 순서
  @Override
  public int filterOrder() {
    return 1;
  }

  // 필터 사용 여부
  @Override
  public boolean shouldFilter() {
    return true;
  }

  // 어디에서 어떤 요청이 들어왔다는 로그 필터
  @Override
  public Object run() throws ZuulException {
    log.info("******** printing logs: ");

    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    log.info("******** " + request.getRequestURI());
    return null;
  }
}
