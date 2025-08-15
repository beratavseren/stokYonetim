package com.example.demo.Dto.Product;

import com.example.demo.Dto.Brand.BrandDto;

public class DetailedProductDto {
    private Long productId;
    private String productName;
    private Long barcodeNumber;
    private int weigth;
    private int volume;
    private BrandDto brandDto;

    public DetailedProductDto(Long productId, String productName, Long barcodeNumber, int weigth, int volume, BrandDto brandDto) {
        this.productId = productId;
        this.productName = productName;
        this.barcodeNumber = barcodeNumber;
        this.weigth = weigth;
        this.volume = volume;
        this.brandDto = brandDto;
    }

    public DetailedProductDto(Long productId, String productName, Long barcodeNumber, BrandDto brandDto) {
        this.productId = productId;
        this.productName = productName;
        this.barcodeNumber = barcodeNumber;
        this.brandDto = brandDto;
    }

    public DetailedProductDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(Long barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public BrandDto getBrandDto() {
        return brandDto;
    }

    public void setBrandDto(BrandDto brandDto) {
        this.brandDto = brandDto;
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
