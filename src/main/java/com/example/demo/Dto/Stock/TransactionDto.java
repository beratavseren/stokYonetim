package com.example.demo.Dto.Stock;

import com.example.demo.Dto.Product.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class TransactionDto {

    private Long werehouseId;
    private List<ProductTransactionDto> productTransactionDtos;

    public TransactionDto(Long werehouseId, List<ProductTransactionDto> productTransactionDtos) {
        this.werehouseId = werehouseId;
        this.productTransactionDtos = productTransactionDtos;
    }

    public TransactionDto() {
    }


    public Long getWerehouseId() {
        return werehouseId;
    }

    public void setWerehouseId(Long werehouseId) {
        this.werehouseId = werehouseId;
    }

    public List<ProductTransactionDto> getProductTransactionDtos() {return productTransactionDtos;}

    public void setProductTransactionDtos(List<ProductTransactionDto> productTransactionDtos) {this.productTransactionDtos = productTransactionDtos;}
}
