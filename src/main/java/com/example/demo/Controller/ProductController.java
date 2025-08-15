package com.example.demo.Controller;

import com.example.demo.Dto.Product.AddProductDto;
import com.example.demo.Dto.Product.DetailedProductDto;
import com.example.demo.Dto.Product.ProductDto;
import com.example.demo.Dto.Product.UpdateProductDto;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService=productService;
    }

    @PostMapping("/add")
    public boolean addProduct(@RequestBody AddProductDto addProductDto)
    {
        return productService.addProduct(addProductDto);
    }


    @DeleteMapping("/delete")
    public boolean deleteProduct(@RequestAttribute Long productId)
    {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/getProducts")
    public List<ProductDto> getProducts()
    {
        return productService.getProducts();
    }

    @GetMapping("/getProduct")
    public DetailedProductDto getDetailedProduct(@RequestAttribute Long productId)
    {
        return productService.getDetailedProduct(productId);
    }

    @GetMapping("getProductByBarcodeNumber")
    public DetailedProductDto getDetailedproductDtoByBarcodeNumber(@RequestAttribute Long barcodeNumber)
    {
        return productService.getDetailedProductByBarcodeNumber(barcodeNumber);
    }
}
