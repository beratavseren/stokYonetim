package com.example.demo.Dto.Product;

public class WerehouseCriticalLevelDto {
    private Long werehouseId;
    private int criticalWeigthLevel;
    private int criticalVolumeLevel;

    public WerehouseCriticalLevelDto(Long werehouseId, int criticalWeigthLevel, int criticalVolumeLevel) {
        this.werehouseId = werehouseId;
        this.criticalWeigthLevel = criticalWeigthLevel;
        this.criticalVolumeLevel = criticalVolumeLevel;
    }

    public WerehouseCriticalLevelDto() {
    }

    public Long getWerehouseId() {
        return werehouseId;
    }

    public void setWerehouseId(Long werehouseId) {
        this.werehouseId = werehouseId;
    }

    public int getCriticalWeigthLevel() {
        return criticalWeigthLevel;
    }

    public void setCriticalWeigthLevel(int criticalWeigthLevel) {
        this.criticalWeigthLevel = criticalWeigthLevel;
    }

    public int getCriticalVolumeLevel() {
        return criticalVolumeLevel;
    }

    public void setCriticalVolumeLevel(int criticalVolumeLevel) {
        this.criticalVolumeLevel = criticalVolumeLevel;
    }
}
