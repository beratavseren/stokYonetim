package com.example.demo.Service;

import com.example.demo.Dto.SignUp.SignUpAdminDto;
import com.example.demo.Dto.SignUp.SignUpWorkerDto;
import com.example.demo.Entity.Admin;
import com.example.demo.Entity.Werehouse;
import com.example.demo.Entity.WerehouseWorker;
import com.example.demo.Repository.AdminRepo;
import com.example.demo.Repository.WerehouseRepo;
import com.example.demo.Repository.WerehouseWorkerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    WerehouseRepo werehouseRepo;
    WerehouseWorkerRepo werehouseWorkerRepo;
    AdminRepo adminRepo;

    @Autowired
    public SignUpService(WerehouseRepo werehouseRepo, WerehouseWorkerRepo werehouseWorkerRepo, AdminRepo adminRepo) {
        this.werehouseRepo = werehouseRepo;
        this.werehouseWorkerRepo = werehouseWorkerRepo;
        this.adminRepo = adminRepo;
    }

    @Transactional
    public boolean signUpAdmin(SignUpAdminDto signUpAdminDto)
    {
        try {
            Admin admin = new Admin(signUpAdminDto.getNameSurname(),signUpAdminDto.getTelNo(), signUpAdminDto.getEmail(), signUpAdminDto.getPassword());
            adminRepo.save(admin);
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public boolean signUpWorker(SignUpWorkerDto signUpWorkerDto)
    {
        try {
            Werehouse werehouse = werehouseRepo.findByWerehouseId(signUpWorkerDto.getWerehouseId());
            WerehouseWorker werehouseWorker = new WerehouseWorker(signUpWorkerDto.getNameSurname(), signUpWorkerDto.getTelNo(), signUpWorkerDto.getEmail(), signUpWorkerDto.getPassword(), werehouse);
            werehouseWorkerRepo.save(werehouseWorker);
            return true;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
