package com.kuriosity.kcc.repository;

import com.kuriosity.kcc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
