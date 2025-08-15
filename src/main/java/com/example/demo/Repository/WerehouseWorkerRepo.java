package com.example.demo.Repository;

import com.example.demo.Entity.WerehouseWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WerehouseWorkerRepo extends JpaRepository<WerehouseWorker,Long> {
    WerehouseWorker findWerehouseWorkerByWerehouseWorkerId(Long werehouseWorkerId);
}
