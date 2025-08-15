package com.example.demo.Repository;

import com.example.demo.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand,Long> {
    Brand findByBrandId(Long brandId);
}
