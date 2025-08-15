package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table
public class ProductTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productTransactionId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Transaction transaction;

    @Column(name = "quantity")
    private int quantity;

    public ProductTransaction(Long productTransactionId, Product product, Transaction transaction, int quantity) {
        this.productTransactionId = productTransactionId;
        this.product = product;
        this.transaction = transaction;
        this.quantity = quantity;
    }

    public ProductTransaction(int quantity, Product product, Transaction transaction) {
        this.quantity = quantity;
        this.product = product;
        this.transaction = transaction;
    }

    public ProductTransaction() {}

    public Long getProductTransactionId() {return productTransactionId;}

    public void setProductTransactionId(Long productTransactionId) {this.productTransactionId = productTransactionId;}

    public Product getProduct() {return product;}

    public void setProduct(Product product) {this.product = product;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

    public Transaction getTransaction() {return transaction;}

    public void setTransaction(Transaction transaction) {this.transaction = transaction;}

}
