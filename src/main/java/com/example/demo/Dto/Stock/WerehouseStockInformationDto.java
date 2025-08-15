package com.example.demo.Dto.Stock;

import java.util.List;

public class WerehouseStockInformationDto {
    private Long werehouseId;
    private String werehouseName;
    private String werehouseLocation;
    private List<ProductStuationForWerehouseDto> productStuationForWerehouseDtos;

    public WerehouseStockInformationDto(Long werehouseId, String werehouseName, String werehouseLocation, List<ProductStuationForWerehouseDto> productStuationForWerehouseDtos) {
        this.werehouseId = werehouseId;
        this.werehouseName = werehouseName;
        this.werehouseLocation = werehouseLocation;
        this.productStuationForWerehouseDtos = productStuationForWerehouseDtos;
    }

    public WerehouseStockInformationDto() {
    }

    public Long getWerehouseId() {
        return werehouseId;
    }

    public void setWerehouseId(Long werehouseId) {
        this.werehouseId = werehouseId;
    }

    public String getWerehouseName() {
        return werehouseName;
    }

    public void setWerehouseName(String werehouseName) {
        this.werehouseName = werehouseName;
    }

    public String getWerehouseLocation() {
        return werehouseLocation;
    }

    public void setWerehouseLocation(String werehouseLocation) {
        this.werehouseLocation = werehouseLocation;
    }

    public List<ProductStuationForWerehouseDto> getProductStuationForWerehouseDtos() {
        return productStuationForWerehouseDtos;
    }

    public void setProductStuationForWerehouseDtos(List<ProductStuationForWerehouseDto> productStuationForWerehouseDtos) {
        this.productStuationForWerehouseDtos = productStuationForWerehouseDtos;
    }
}
