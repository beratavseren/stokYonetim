package com.example.demo.Dto.Stock;

public class ProductTransactionDto {

    private Long productId;
    private int quantity;

    public ProductTransactionDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductTransactionDto() {}

    public Long getProductId() {return productId;}
    public void setProductId(Long productId) {this.productId = productId;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

}
