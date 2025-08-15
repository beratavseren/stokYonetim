package com.example.demo.Service;

import com.example.demo.Dto.WereHouse.*;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductWerehouse;
import com.example.demo.Entity.Werehouse;
import com.example.demo.Repository.ProductRepo;
import com.example.demo.Repository.ProductWerehouseRepo;
import com.example.demo.Repository.WerehouseRepo;
import com.example.demo.Repository.WerehouseWorkerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WerehouseService {
    WerehouseRepo werehouseRepo;
    WerehouseWorkerRepo werehouseWorkerRepo;
    ProductWerehouseRepo productWerehouseRepo;
    ProductRepo productRepo;

    @Autowired
    public WerehouseService(WerehouseRepo werehouseRepo, ProductRepo productRepo, WerehouseWorkerRepo werehouseWorkerRepo, ProductWerehouseRepo productWerehouseRepo) {
        this.werehouseRepo = werehouseRepo;
        this.werehouseWorkerRepo = werehouseWorkerRepo;
        this.productWerehouseRepo = productWerehouseRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public boolean addWerehouse(AddWerehouseDto addWerehouseDto) {
        try {
            Werehouse werehouse = new Werehouse(addWerehouseDto.getWerehouseName(), addWerehouseDto.getWerehouseLocation(), addWerehouseDto.getWeigthLimit(), addWerehouseDto.getVolumeLimit());
            werehouse = werehouseRepo.save(werehouse);

            List<Product> products = productRepo.findAll();

            for (Product product : products) {
                productWerehouseRepo.save(new ProductWerehouse(product, werehouse, 0));
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean updateWerehouse(UpdateWerehouseDto updateWerehouseDto) {
        try {
            Werehouse werehouse = werehouseRepo.findByWerehouseId(updateWerehouseDto.getWerehouseId());
            boolean changeControl = false;
            if (!werehouse.getWerehouseName().equals(updateWerehouseDto.getWerehouseName()))
            {
                werehouse.setWerehouseName(updateWerehouseDto.getWerehouseName());
                changeControl=true;
            }

            if (werehouse.getWeigthLimit() != updateWerehouseDto.getWeigthLimit())
            {
                werehouse.setWeigthLimit(updateWerehouseDto.getWeigthLimit());
                changeControl=true;
            }

            if (werehouse.getVolumeLimit() != updateWerehouseDto.getVolumeLimit())
            {
                werehouse.setVolumeLimit(updateWerehouseDto.getVolumeLimit());
                changeControl=true;
            }

            if (changeControl)
            {
                werehouseRepo.save(werehouse);
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public DetailedWerehouseDto getWerehouse(Long werehouseId){
        try {
            Werehouse werehouse = werehouseRepo.findByWerehouseId(werehouseId);

            return new DetailedWerehouseDto(werehouse.getWerehouseId(), werehouse.getWerehouseName(), werehouse.getWerehouseLocation(), werehouse.getWeigthLimit(), werehouse.getVolumeLimit(), werehouse.getCurrentWeigth(), werehouse.getCurrentVolume());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new DetailedWerehouseDto();
        }
    }

    @Transactional
    public List<WerehouseDto> getWerehouses()
    {
        try {
            List<Werehouse> werehouses = werehouseRepo.findAll();

            List<WerehouseDto> werehouseDtos = new ArrayList<>();
            for (Werehouse werehouse:werehouses)
            {
                werehouseDtos.add(new WerehouseDto(werehouse.getWerehouseId(), werehouse.getWerehouseName()));
            }

            return werehouseDtos;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Transactional
    public boolean deleteWerehouse(Long werehouseId) {
        try {
            Werehouse werehouse = werehouseRepo.findByWerehouseId(werehouseId);

            List<ProductWerehouse> productWerehouses = productWerehouseRepo.findProductWerehousesByWerehouse(werehouse);

            for (ProductWerehouse productWerehouse:productWerehouses)
            {
                if (productWerehouse.getQuantity()!=0)
                {
                    return false;
                }
            }

            productWerehouseRepo.deleteProductWerehousesByWerehouse(werehouse);
            werehouseRepo.delete(werehouse);

            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public List<WerehouseDto> getWerehousesWithWeigth() {
        try {
            List<WerehouseDto> werehouseDtos = new ArrayList<>();
            List<Werehouse> werehouses = werehouseRepo.findAll();

            for (Werehouse werehouse:werehouses)
            {
                werehouseDtos.add(new WerehouseDto(werehouse.getWerehouseId(), werehouse.getWerehouseName(), werehouse.getWeigthLimit(),  werehouse.getVolumeLimit()));
            }

            return werehouseDtos;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
