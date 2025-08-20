package com.example.demo.Service;

import com.example.demo.Dto.Stock.*;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import jakarta.transaction.Transactional;
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
    public boolean inProduct(AddTransactionDto addTransactionDto) {
        try {
            Werehouse werehouse = werehouseRepo.findByWerehouseId(addTransactionDto.getWerehouseId());

            //check werehouse capacity
            // admin tarafından ileri tarihli bir transaction verilecekse bu tarih gelene kadar depoda yer açılabilir o yüzden buradaki kontrol werehouse worker transacationı onaylarken yapılabilir ancak eğer bahsi geçen ileri tarihe kadar eğer depoda yer açılmazsa o zaman ne olacak bu riski almaya değer mi?
            List<Product> products = new ArrayList<>();

            int totalWeight = 0;

            for (ProductTransactionDto productTransactionDto : addTransactionDto.getProductTransactionDtos()) {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                products.add(product);
                totalWeight = totalWeight + product.getWeight() * productTransactionDto.getQuantity();
            }

            if ( totalWeight + werehouse.getCurrentWeigth() > werehouse.getWeigthLimit())
            {
                throw new RuntimeException("The werehouse is full.");
            }

            Transaction transaction = transactionRepo.save(new Transaction(werehouse,TransactionType.IN,false));

            for (ProductTransactionDto productTransactionDto : addTransactionDto.getProductTransactionDtos()) {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                productTransactionRepo.save(new ProductTransaction(productTransactionDto.getQuantity(),product,transaction));
            }

            //productWerehouse a kaydet.
            for(ProductTransactionDto productTransactionDto : addTransactionDto.getProductTransactionDtos())
            {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                ProductWerehouse productWerehouse = productWerehouseRepo.findProductWerehousesByProductAndWerehouse(product, werehouse);
                productWerehouse.setQuantity(productWerehouse.getQuantity() + productTransactionDto.getQuantity());
                productWerehouseRepo.save(productWerehouse);
            }

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    //add alert for movement
    //add critical stock level
    @Transactional
    public boolean outProduct(AddTransactionDto addTransactionDto) {
        try {

            Werehouse werehouse = werehouseRepo.findByWerehouseId(addTransactionDto.getWerehouseId());

            //checking werehouse stock if had enough
            for (ProductTransactionDto productTransactionDto : addTransactionDto.getProductTransactionDtos()) {

                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                ProductWerehouse productWerehouse = productWerehouseRepo.findProductWerehousesByProductAndWerehouse(product, werehouse);

                if (productWerehouse.getQuantity() < productTransactionDto.getQuantity())
                {
                    throw new RuntimeException("The werehouse does not have enough stock.");
                }
            }

            // transaction ve ara tablolarına kayıt
            Transaction transaction = transactionRepo.save(new Transaction(werehouse, TransactionType.OUT, false));

            for(ProductTransactionDto productTransactionDto : addTransactionDto.getProductTransactionDtos())
            {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                productTransactionRepo.save(new ProductTransaction(productTransactionDto.getQuantity(),product,transaction));
            }

            //productWerehouse ara tablosuna ekle
            for(ProductTransactionDto productTransactionDto : addTransactionDto.getProductTransactionDtos())
            {
                Product product = productRepo.findByProductId(productTransactionDto.getProductId());
                ProductWerehouse productWerehouse=productWerehouseRepo.findProductWerehousesByProductAndWerehouse(product, werehouse);
                productWerehouse.setQuantity(productWerehouse.getQuantity()-productTransactionDto.getQuantity());
                productWerehouseRepo.save(productWerehouse);
            }

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public boolean betweenWerehouses(TransactionBetweenWerehouseDto transactionBetweenWerehouseDto) {
        try {
            boolean checkOutProduct;
            boolean checkInProduct;

            checkOutProduct = outProduct(new AddTransactionDto(transactionBetweenWerehouseDto.getFromWerehouseId(), transactionBetweenWerehouseDto.getProductTransactionDtos()));

            checkInProduct = inProduct(new AddTransactionDto(transactionBetweenWerehouseDto.getToWerehouseId(), transactionBetweenWerehouseDto.getProductTransactionDtos()));

            if (!checkOutProduct || !checkInProduct)
            {
                throw new RuntimeException("There is an error in the transaction");
            }

            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public DetailedTransactionDto getDetailedTransaction(Long transactionId)
    {
        try {
            Transaction transaction = transactionRepo.findTransactionByTransactionId(transactionId);

            List<ProductTransaction> productTransactions = productTransactionRepo.findProductTransactionsByTransaction(transaction);
            List<ProductTransactionDto> productTransactionDtos = new ArrayList<>();


            for (ProductTransaction productTransaction:productTransactions)
            {
                productTransactionDtos.add(new ProductTransactionDto(productTransaction.getProduct().getProductId(),productTransaction.getQuantity()));
            }

            return new DetailedTransactionDto(transaction.getTransactionId(), transaction.getTransactionType(), transaction.getWerehouse().getWerehouseName(), transaction.isSituation(), productTransactionDtos);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<TransactionDto> getTransactionsWithFilter(TransactionType transactionType, Long werehouseId, Boolean transactionStuation)
    {
        try {
            List<Transaction> transactions = transactionRepo.findTransactionsByTransactionTypeAndWerehouseIdAndSituation(transactionType, werehouseId, transactionStuation);
            List<TransactionDto> transactionDtos = new ArrayList<>();

            for (Transaction transaction:transactions)
            {
                transactionDtos.add(new TransactionDto(transaction.getTransactionId(), transaction.getTransactionType(), transaction.getWerehouse().getWerehouseName()));
            }

            return transactionDtos;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}