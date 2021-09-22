package com.demo.emt.carscatalog.service;

import com.demo.emt.carscatalog.domain.exceptions.VehicleIdNotExistException;
import com.demo.emt.carscatalog.domain.exceptions.VehicleUseCategoryIdNotExistException;
import com.demo.emt.carscatalog.domain.model.Vehicle;
import com.demo.emt.carscatalog.domain.model.VehicleUseCategory;
import com.demo.emt.carscatalog.domain.model.ids.VehicleId;
import com.demo.emt.carscatalog.domain.model.ids.VehicleUseCategoryId;
import com.demo.emt.carscatalog.service.forms.VehicleDto;
import com.demo.emt.carscatalog.service.forms.VehicleForm;
import com.demo.emt.carscatalog.service.forms.VehicleUseOrderForm;

import java.util.List;
import java.util.Optional;

public interface VehicleUseCategoryService {
    //vo ovojd del mi e kako naracka na avtomobili i vo nea ima stavki
    VehicleUseCategoryId placeVehicleOrder(VehicleUseOrderForm vehicleUseForm);

    List<VehicleUseCategory> findAll();
    List<Vehicle> findAllVehicles();

    Optional<VehicleUseCategory> findById(VehicleUseCategoryId id);

    void addItem(VehicleUseCategoryId vehicleUseCategoryOrderId, VehicleForm VehicleForm) throws VehicleUseCategoryIdNotExistException;

    void deleteItem(VehicleUseCategoryId vehicleUseCategoryOrderId, VehicleId vehicleId) throws VehicleUseCategoryIdNotExistException, VehicleIdNotExistException;

    Vehicle save(VehicleDto vehicleDto);

    void deleteVehicleById(VehicleId of);

    Optional<Vehicle> findVehicleById(VehicleId of);
}
