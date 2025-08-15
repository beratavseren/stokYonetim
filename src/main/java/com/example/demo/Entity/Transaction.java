package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    private Werehouse werehouse;


    public Transaction(Long transactionId, Werehouse werehouse) {
        this.transactionId = transactionId;
        this.werehouse = werehouse;
    }

    public Transaction(Werehouse werehouse) {
        this.werehouse = werehouse;
    }

    public Transaction() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }


    public Werehouse getWerehouse() {
        return werehouse;
    }

    public void setWerehouse(Werehouse werehouse) {
        this.werehouse = werehouse;
    }

}
