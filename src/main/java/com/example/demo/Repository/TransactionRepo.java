package com.example.demo.Repository;

import com.example.demo.Entity.Transaction;
import com.example.demo.Entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByTransactionType(TransactionType transactionType);

    @Query("SELECT t FROM Transaction t " +
           "WHERE (:transactionType IS NULL OR t.transactionType = :transactionType) " +
           "AND (:werehouseId IS NULL OR t.werehouse.werehouseId = :werehouseId) " +
           "AND (:situation IS NULL OR t.situation = :situation)")
    List<Transaction> findTransactionsByTransactionTypeAndWerehouseIdAndSituation(
            @Param("transactionType") TransactionType transactionType,
            @Param("werehouseId") Long werehouseId,
            @Param("situation") Boolean situation);

    Transaction findTransactionByTransactionId(Long transactionId);
}
