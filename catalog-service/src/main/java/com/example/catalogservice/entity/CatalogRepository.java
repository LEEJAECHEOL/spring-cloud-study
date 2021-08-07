package com.example.catalogservice.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity,Long> {
  CatalogEntity findByProductId(String productId);
}
