package com.example.demo.Repository;

import com.example.demo.Entity.Werehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WerehouseRepo extends JpaRepository<Werehouse,Long> {
    Werehouse findByWerehouseId(Long werehouseId);
}
