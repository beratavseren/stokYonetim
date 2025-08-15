package com.example.demo.Repository;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductWerehouse;
import com.example.demo.Entity.Werehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductWerehouseRepo extends JpaRepository<ProductWerehouse,Long> {
    public List<ProductWerehouse> findProductWerehousesByProduct(Product product);
    ProductWerehouse findProductWerehousesByProductAndWerehouse(Product product, Werehouse werehouse);

    List<ProductWerehouse> findProductWerehousesByWerehouse(Werehouse werehouse);

    void deleteProductWerehousesByWerehouse(Werehouse werehouse);
}
