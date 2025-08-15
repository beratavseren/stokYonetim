package com.example.demo.Service;

import com.example.demo.Dto.Brand.BrandDto;
import com.example.demo.Dto.Product.AddProductDto;
import com.example.demo.Dto.Product.DetailedProductDto;
import com.example.demo.Dto.Product.ProductDto;
import com.example.demo.Entity.Brand;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductWerehouse;
import com.example.demo.Entity.Werehouse;
import com.example.demo.Repository.BrandRepo;
import com.example.demo.Repository.ProductRepo;
import com.example.demo.Repository.ProductWerehouseRepo;
import com.example.demo.Repository.WerehouseRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//update yok çünkü productta update edilebilecek bir şey yok(1 brand var, id değişmez, isim değişmek mantıksız, barcode değişimi veri bütünlüğünü tehlikeye atabilir(veri tabanı triigerı veya backend triggerı ile yapılabilir ama gerek var mı barcode u ürün üreticisi yapar))
@Service
public class ProductService {
    ProductRepo productRepo;
    WerehouseRepo werehouseRepo;
    ProductWerehouseRepo productWerehouseRepo;
    BrandRepo brandRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, BrandRepo brandRepo, WerehouseRepo werehouseRepo, ProductWerehouseRepo productWerehouseRepo)
    {
        this.productRepo=productRepo;
        this.werehouseRepo=werehouseRepo;
        this.brandRepo=brandRepo;
        this.productWerehouseRepo=productWerehouseRepo;
    }

    @Transactional
    public boolean addProduct(AddProductDto addProductDto)
    {
        try {
            Brand brand = brandRepo.findByBrandId(addProductDto.getBrandDto().getBrandId());
            Product product = new Product(addProductDto.getProductName(), addProductDto.getBarcodeNumber(), addProductDto.getWeigth(), addProductDto.getVolume(), brand);
            product = productRepo.save(product);

            List<Werehouse> werehouses = werehouseRepo.findAll();

            for (Werehouse werehouse:werehouses) {
                productWerehouseRepo.save(new ProductWerehouse(product, werehouse ,0));
            }

            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean deleteProduct(Long productId)
    {
        try {
            Product product = productRepo.findByProductId(productId);
            List<ProductWerehouse> productWerehouses = productWerehouseRepo.findProductWerehousesByProduct(product);
            for (ProductWerehouse productWerehouse : productWerehouses) {
                if (productWerehouse.getQuantity()!=0)
                {
                    return false;
                }
            }
            productRepo.deleteById(productId);
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public List<ProductDto> getProducts()
    {
        try {
            List<Product> products = productRepo.findAll();
            List<ProductDto> productDtos = new ArrayList<>();

            for (Product product:products)
            {
                productDtos.add(new ProductDto(product.getProductId(), product.getProductName()));
            }

            return productDtos;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    //Ürün detay ekranı (mobil) buraya istatistikleri de ekle
    @Transactional
    public DetailedProductDto getDetailedProduct(Long productId)
    {
        try {
            Product product = productRepo.findByProductId(productId);

            return new DetailedProductDto(product.getProductId(), product.getProductName(), product.getBarcodeNumber(), product.getWeight(), product.getVolume(), new BrandDto(product.getBrand().getBrandId(), product.getBrand().getBrandName()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new DetailedProductDto();
        }
    }

    @Transactional
    public DetailedProductDto getDetailedProductByBarcodeNumber(Long barcodeNumber)
    {
        try {
            Product product = productRepo.findByBarcodeNumber(barcodeNumber);

            return new DetailedProductDto(product.getProductId(), product.getProductName(), product.getBarcodeNumber(), product.getWeight(), product.getVolume(), new BrandDto(product.getBrand().getBrandId(), product.getBrand().getBrandName()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new DetailedProductDto();
        }
    }

}
