package com.example.demo.Controller;

import com.example.demo.Dto.Stock.TransactionDto;
import com.example.demo.Dto.Stock.TransactionBetweenWerehouseDto;
import com.example.demo.Service.AdminStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class AdminStockController {
    AdminStockService adminStockService;

    @Autowired
    public AdminStockController(AdminStockService adminStockService)
    {
        this.adminStockService = adminStockService;
    }

    @PutMapping("/in")
    public boolean buyProduct(@RequestBody TransactionDto transactionDto)
    {
        return adminStockService.inProduct(transactionDto);
    }

    @PutMapping("/out")
    public boolean sellProduct(@RequestBody TransactionDto transactionDto) {
        return adminStockService.outProduct(transactionDto);
    }

    @PutMapping("/betweenWerehouses")
    public boolean betweenWerehouses(@RequestBody TransactionBetweenWerehouseDto transactionBetweenWerehouseDto)
    {
        return adminStockService.betweenWerehouses(transactionBetweenWerehouseDto);
    }
}
