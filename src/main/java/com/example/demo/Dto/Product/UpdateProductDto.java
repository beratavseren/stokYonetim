package com.example.demo.Dto.Product;

public class UpdateProductDto {
    private Long productId;
    private int weigth;
    private int volume;

    public UpdateProductDto(Long productId, int weigth, int volume) {
        this.productId = productId;
        this.weigth = weigth;
        this.volume = volume;
    }

    public UpdateProductDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
