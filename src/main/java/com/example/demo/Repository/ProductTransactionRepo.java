package com.example.demo.Repository;

import com.example.demo.Entity.ProductTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTransactionRepo extends JpaRepository<ProductTransaction,Long> {
}
