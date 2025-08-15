package com.example.demo.Service;

import com.example.demo.Dto.Stock.ProductTransactionDto;
import com.example.demo.Dto.Stock.TransactionDto;
import com.example.demo.Dto.Stock.TransactionBetweenWerehouseDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminStockService {

    ProductRepo productRepo;
    ProductWerehouseRepo productWerehouseRepo;
    WerehouseRepo werehouseRepo;
    TransactionRepo transactionRepo;
    ProductTransactionRepo productTransactionRepo;

    @Autowired
    public AdminStockService(ProductRepo productRepo, ProductWerehouseRepo productWerehouseRepo, WerehouseRepo werehouseRepo,  TransactionRepo transactionRepo, ProductTransactionRepo productTransactionRepo)
    {
        this.productRepo=productRepo;
        this.werehouseRepo=werehouseRepo;
        this.productWerehouseRepo=productWerehouseRepo;
        this.transactionRepo=transactionRepo;
        this.productTransactionRepo=productTransactionRepo;
    }

    //check werehouse capacity for the in movement
    //add werehouse capacity to werehouse entity
    //add alert of movement
    //log it
    @Transactional
    public boolean inProduct(TransactionDto transactionDto)
    {
        try {
            Werehouse werehouse = werehouseRepo.findByWerehouseId(transactionDto.getWerehouseId());

            //check werehouse capacity
            // admin tarafından ileri tarihli bir transaction verilecekse bu tarih gelene kadar depoda yer açılabilir o yüzden buradaki kontrol werehouse worker transacationı onaylarken yapılabilir ancak eğer bahsi geçen ileri tarihe kadar eğer depoda yer açılmazsa o zaman ne olacak bu riski almaya değer mi?
            List<Product> products = new ArrayList<>();
            int totalWeight = 0;
            for (ProductTransactionDto productTransactionDto : transactionDto.getProductTransactionDtos()) {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                products.add(product);
                totalWeight = totalWeight + product.getWeight() * productTransactionDto.getQuantity();
            }

            if ( totalWeight + werehouse.getCurrentWeigth() > werehouse.getWeigthLimit())
            {
                return false;
            }

            Transaction transaction = transactionRepo.save(new Transaction(werehouse));

            for (Product product : products) {


            }



            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //check werehouse stock if werehouse has enough to move out product
    //add alert for movement
    //add critical stock level
    @Transactional
    public boolean outProduct(OutProductDto outProductDto)
    {
        try {
            Product product = productRepo.findByProductId(outProductDto.getProductId());
            Werehouse werehouse = werehouseRepo.findByWerehouseId(outProductDto.getWerehouseId());
            ProductWerehouse productWerehouse = productWerehouseRepo.findProductWerehousesByProductAndWerehouse(product, werehouse);

            if (outProductDto.getQuantity()>0)
            {
                productWerehouse.setQuantity(productWerehouse.getQuantity() + outProductDto.getQuantity());
                productWerehouseRepo.save(productWerehouse);
            }

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean betweenWerehouses(TransactionBetweenWerehouseDto transactionBetweenWerehouseDto)
    {
        try {
            outProduct(new OutProductDto(transactionBetweenWerehouseDto.getProductId(), transactionBetweenWerehouseDto.getFromWerehouseId(), transactionBetweenWerehouseDto.getQuantity()));
            inProduct(new TransactionDto(transactionBetweenWerehouseDto.getProductId(), transactionBetweenWerehouseDto.getToWerehouseId(), transactionBetweenWerehouseDto.getQuantity()));

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
}