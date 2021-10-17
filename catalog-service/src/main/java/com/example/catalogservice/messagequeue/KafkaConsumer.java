package com.example.catalogservice.messagequeue;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.entity.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// 리스너를 이용해서 데이터를 가져오고 그 데이터를 데이터베이스에 업데이트 할려 함
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
  private final CatalogRepository catalogRepository;

  @KafkaListener(topics = "example-catalog-topic")
  public void updateQty(String kafkaMessage) {
    log.info("Kafka Message : -> {}", kafkaMessage);

    Map<Object, Object> map = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    try {
      map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
    }catch (JsonProcessingException e){
      e.printStackTrace();;
    }
    CatalogEntity entity = catalogRepository.findByProductId((String)map.get("productId"));

    if(entity != null){
      entity.setStock(entity.getStock() - (Integer) map.get("qty"));
      catalogRepository.save(entity);
    }
  }
}
