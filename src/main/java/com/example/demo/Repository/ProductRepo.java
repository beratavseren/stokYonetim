package com.example.demo.Repository;

import com.example.demo.Entity.Brand;
import com.example.demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {
    Product findByProductId(Long productId);
    Product findByBarcodeNumber(Long barcodeNumber);
    List<Product> findProductsByBrand(Brand brand);
}
