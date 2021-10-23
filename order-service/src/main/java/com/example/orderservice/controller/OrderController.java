package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {
  private final Environment env;
  private final OrderService orderService;
  private final KafkaProducer kafkaProducer;
  private final OrderProducer orderProducer;

  @GetMapping("/health_check")
  public String status(){
    return String.format("It's Working in Order Service on Port %s",
      env.getProperty("local.server.port"));
  }

  //주문
  @PostMapping("/{userId}/orders")
  public ResponseEntity<ResponseOrder> createUser(@PathVariable("userId") String userId,
                                                  @RequestBody RequestOrder orderDetails){
    log.info("Before add orders data");
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
    orderDto.setUserId(userId);
    /* jpa */
    OrderDto createdOrder = orderService.createOrder(orderDto);
    ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

    /* kafka */
//    orderDto.setOrderId(UUID.randomUUID().toString());
//    orderDto.setTotalPrice( orderDetails.getQty() * orderDetails.getUnitPrice());

    /* 카프에 메시지를 전달한은 동작을 추가한다. */
    /* send this order to the kafa */
//    kafkaProducer.send("example-catalog-topic", orderDto);
//    orderProducer.send("orders", orderDto);

//    ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

    log.info("After add orders data");
    return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId){
    log.info("Before retrieve orders data");
    Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);

    List<ResponseOrder> result = new ArrayList<>();
    orders.forEach(v->{
      result.add(new ModelMapper().map(v,ResponseOrder.class));
    });
    log.info("After retrieve orders data");

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
