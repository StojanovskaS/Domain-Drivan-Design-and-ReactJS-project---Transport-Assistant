package com.demo.emt.carscatalog.xport.rest;

import com.demo.emt.carscatalog.domain.model.Vehicle;
import com.demo.emt.carscatalog.domain.model.ids.VehicleId;
import com.demo.emt.carscatalog.service.VehicleUseCategoryService;
import com.demo.emt.carscatalog.service.forms.VehicleDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/vehicles")
@AllArgsConstructor
public class VehicleController {
    //za povrzuvanje so react
    private final VehicleUseCategoryService vehicleUseCategoryService;

    @GetMapping
    private List<Vehicle> findAll() {
        return this.vehicleUseCategoryService.findAllVehicles();
    }


    @PostMapping("/add")
    public ResponseEntity<Vehicle> save(@RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle =this.vehicleUseCategoryService.save(vehicleDto);
        if (vehicle != null) {
            return ResponseEntity.ok().body(vehicle);
        } else {
            return ResponseEntity.badRequest().build();
        }
//        return this.vehicleUseCategoryService.save(vehicleDto)
//                .map(product -> ResponseEntity.ok().body(product))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


}
