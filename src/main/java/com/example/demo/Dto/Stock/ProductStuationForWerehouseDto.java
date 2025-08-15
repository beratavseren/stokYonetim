package com.example.demo.Dto.Stock;

public class ProductStuationForWerehouseDto {
    private Long productId;
    private int quantity;
    private String productName;

    public ProductStuationForWerehouseDto(Long productId, int quantity, String productName) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
    }

    public ProductStuationForWerehouseDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
