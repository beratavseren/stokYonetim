package com.example.demo.Controller;

import com.example.demo.Dto.WereHouse.AddWerehouseDto;
import com.example.demo.Dto.WereHouse.DetailedWerehouseDto;
import com.example.demo.Dto.WereHouse.UpdateWerehouseDto;
import com.example.demo.Dto.WereHouse.WerehouseDto;
import com.example.demo.Service.WerehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/werehouse")
public class WerehouseController {
    WerehouseService werehouseService;

    @Autowired
    public WerehouseController(WerehouseService wereHouseService)
    {
        this.werehouseService = wereHouseService;
    }

    @PostMapping("/add")
    public boolean addWerehouse(@RequestBody AddWerehouseDto addWerehousedto)
    {
        return werehouseService.addWerehouse(addWerehousedto);
    }

    @PutMapping("/update")
    public boolean updateWerehouse(@RequestBody UpdateWerehouseDto updateWerehouseDto)
    {
        return werehouseService.updateWerehouse(updateWerehouseDto);
    }

    @DeleteMapping("/delete/{werehouseId}")
    public boolean deleteWerehouse(@PathVariable Long werehouseId)
    {
        return werehouseService.deleteWerehouse(werehouseId);
    }

    @GetMapping("/getWerehouse/{werehouseId}")
    public DetailedWerehouseDto getWerehouse(@PathVariable Long werehouseId)
    {
        return werehouseService.getWerehouse(werehouseId);
    }

    @GetMapping("/getWerehouses")
    public List<WerehouseDto> getWerehouses()
    {
        return werehouseService.getWerehouses();
    }

    @GetMapping("/getWerehousesWithWeigth")
    public List<WerehouseDto> getWerehousesWithWeigth()
    {
        return werehouseService.getWerehousesWithWeigth();
    }
}
