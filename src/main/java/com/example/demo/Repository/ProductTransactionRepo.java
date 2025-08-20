package com.example.demo.Repository;

import com.example.demo.Entity.ProductTransaction;
import com.example.demo.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTransactionRepo extends JpaRepository<ProductTransaction,Long> {
    List<ProductTransaction> findProductTransactionsByTransaction(Transaction transaction);
}
