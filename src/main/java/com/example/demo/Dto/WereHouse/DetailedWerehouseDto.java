package com.example.demo.Dto.WereHouse;

public class DetailedWerehouseDto {
    private Long werehouseId;
    private String werehouseName;
    private String werehouseLocation;
    private Double weigthLimit;
    private Double volumeLimit;
    private Double currentWeigth;
    private Double currentVolume;

    public DetailedWerehouseDto(Long werehouseId, String werehouseName, String werehouseLocation, Double weigthLimit, Double volumeLimit, Double currentWeigth, Double currentVolume) {
        this.werehouseId = werehouseId;
        this.werehouseName = werehouseName;
        this.werehouseLocation = werehouseLocation;
        this.weigthLimit = weigthLimit;
        this.volumeLimit = volumeLimit;
        this.currentWeigth = currentWeigth;
        this.currentVolume = currentVolume;
    }

    public DetailedWerehouseDto() {
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

    public Double getWeigthLimit() {
        return weigthLimit;
    }

    public void setWeigthLimit(Double weigthLimit) {
        this.weigthLimit = weigthLimit;
    }

    public Double getVolumeLimit() {
        return volumeLimit;
    }

    public void setVolumeLimit(Double volumeLimit) {
        this.volumeLimit = volumeLimit;
    }

    public Double getCurrentWeigth() {
        return currentWeigth;
    }

    public void setCurrentWeigth(Double currentWeigth) {
        this.currentWeigth = currentWeigth;
    }

    public Double getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(Double currentVolume) {
        this.currentVolume = currentVolume;
    }
}
