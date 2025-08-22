package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_werehouse")
public class ProductWerehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productWerehouseId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Werehouse werehouse;

    @Column(name = "quantity")
    private int quantity;

    //critical stock level not weight and volume fix that
    //user can set a critical stock level and update it for every product and every werehouse


    //critical stock level and weight and volume level notification
    @Column(name = "criticalLevel")
    private int criticalWeigthLevel;

    @Column(name = "criticalVolumeLevel")
    private int criticalVolumeLevel;

    public ProductWerehouse(Long productWerehouseId, Product product, Werehouse werehouse, int quantity, int criticalWeigthLevel, int criticalVolumeLevel) {
        this.productWerehouseId = productWerehouseId;
        this.product = product;
        this.werehouse = werehouse;
        this.quantity = quantity;
        this.criticalWeigthLevel = criticalWeigthLevel;
        this.criticalVolumeLevel = criticalVolumeLevel;
    }

    public ProductWerehouse(Long productWerehouseId, Product product, Werehouse werehouse, int quantity, int criticalWeigthLevel) {
        this.productWerehouseId = productWerehouseId;
        this.product = product;
        this.werehouse = werehouse;
        this.quantity = quantity;
        this.criticalWeigthLevel = criticalWeigthLevel;
    }

    public ProductWerehouse(Long productWerehouseId, Product product, Werehouse werehouse, int quantity) {
        this.productWerehouseId = productWerehouseId;
        this.product = product;
        this.werehouse = werehouse;
        this.quantity = quantity;
    }

    public ProductWerehouse(Product product, Werehouse werehouse, int quantity, int criticalWeigthLevel) {
        this.product = product;
        this.werehouse = werehouse;
        this.quantity = quantity;
        this.criticalWeigthLevel = criticalWeigthLevel;
    }

    public ProductWerehouse(Product product, Werehouse werehouse, int quantity) {
        this.product = product;
        this.werehouse = werehouse;
        this.quantity = quantity;
    }

    public ProductWerehouse() {
    }

    public Long getProductWerehouseId() {
        return productWerehouseId;
    }

    public void setProductWerehouseId(Long productWerehouseId) {
        this.productWerehouseId = productWerehouseId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Werehouse getWerehouse() {
        return werehouse;
    }

    public void setWerehouse(Werehouse werehouse) {
        this.werehouse = werehouse;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCriticalWeigthLevel() {
        return criticalWeigthLevel;
    }

    public void setCriticalWeigthLevel(int criticalLevel) {
        this.criticalWeigthLevel = criticalLevel;
    }

    public int getCriticalVolumeLevel() {
        return criticalVolumeLevel;
    }

    public void setCriticalVolumeLevel(int criticalVolumeLevel) {
        this.criticalVolumeLevel = criticalVolumeLevel;
    }
}
