package com.example.demo.Service;

import com.example.demo.Dto.Stock.ProductTransactionDto;
import com.example.demo.Dto.Stock.TransactionDto;
import com.example.demo.Dto.Stock.TransactionBetweenWerehouseDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
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

    //add alert of movement
    //log it
    @Transactional
    public boolean inProduct(TransactionDto transactionDto) {
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

            for (ProductTransactionDto productTransactionDto : transactionDto.getProductTransactionDtos()) {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                productTransactionRepo.save(new ProductTransaction(productTransactionDto.getQuantity(),product,transaction));
            }

            //productWerehouse a kaydet.
            for(ProductTransactionDto productTransactionDto : transactionDto.getProductTransactionDtos())
            {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                ProductWerehouse productWerehouse = productWerehouseRepo.findProductWerehousesByProductAndWerehouse(product, werehouse);
                productWerehouse.setQuantity(productWerehouse.getQuantity() + productTransactionDto.getQuantity());
            }

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }


    //add alert for movement
    //add critical stock level
    @Transactional
    public boolean outProduct(TransactionDto transactionDto) {
        try {

            Werehouse werehouse = werehouseRepo.findByWerehouseId(transactionDto.getWerehouseId());

            //checking werehouse stock if had enough
            for (ProductTransactionDto productTransactionDto : transactionDto.getProductTransactionDtos()) {

                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                ProductWerehouse productWerehouse = productWerehouseRepo.findProductWerehousesByProductAndWerehouse(product, werehouse);

                if (productWerehouse.getQuantity() < productTransactionDto.getQuantity())
                {
                    return false;
                }
            }

            // transaction ve ara tablolarına kayıt
            Transaction transaction = transactionRepo.save(new Transaction(werehouse));

            for(ProductTransactionDto productTransactionDto : transactionDto.getProductTransactionDtos())
            {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                productTransactionRepo.save(new ProductTransaction(productTransactionDto.getQuantity(),product,transaction));
            }

            //productWerehouse ara tablosuna ekle
            for(ProductTransactionDto productTransactionDto : transactionDto.getProductTransactionDtos())
            {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                ProductWerehouse productWerehouse=productWerehouseRepo.findProductWerehousesByProductAndWerehouse(product, werehouse);
                productWerehouse.setQuantity(productWerehouse.getQuantity()-productTransactionDto.getQuantity());
            }

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean betweenWerehouses(TransactionBetweenWerehouseDto transactionBetweenWerehouseDto) {
        try {
            outProduct(new TransactionDto(transactionBetweenWerehouseDto.getFromWerehouseId(), transactionBetweenWerehouseDto.getProductTransactionDtos()));
            inProduct(new TransactionDto(transactionBetweenWerehouseDto.getToWerehouseId(), transactionBetweenWerehouseDto.getProductTransactionDtos()));

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
}