package com.example.demo.Dto.WereHouse;

public class ProductQuantityDtoForDetailedWerehouseDto {
    private String productName;
    private int quantity;

    public ProductQuantityDtoForDetailedWerehouseDto(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public ProductQuantityDtoForDetailedWerehouseDto() {
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
