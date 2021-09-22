package com.demo.emt.carscatalog.service.implementation;

import com.demo.emt.carscatalog.domain.exceptions.VehicleIdNotExistException;
import com.demo.emt.carscatalog.domain.exceptions.VehicleUseCategoryIdNotExistException;
import com.demo.emt.carscatalog.domain.model.Vehicle;
import com.demo.emt.carscatalog.domain.model.VehicleUseCategory;
import com.demo.emt.carscatalog.domain.model.ids.VehicleId;
import com.demo.emt.carscatalog.domain.model.ids.VehicleUseCategoryId;
import com.demo.emt.carscatalog.domain.repository.VehicleRepository;
import com.demo.emt.carscatalog.domain.repository.VehicleUseCategoryRepository;
import com.demo.emt.carscatalog.service.VehicleUseCategoryService;
import com.demo.emt.carscatalog.service.forms.VehicleDto;
import com.demo.emt.carscatalog.service.forms.VehicleForm;
import com.demo.emt.carscatalog.service.forms.VehicleUseOrderForm;
import com.demo.emt.sharedkernel.domain.events.vehicle.VehicleItemCreated;
import com.demo.emt.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class VehicleUseCategoryServiceImpl implements VehicleUseCategoryService {
    private VehicleUseCategoryRepository vehicleUseCategoryRepository;
    private final Validator validator;
    private final DomainEventPublisher domainEventPublisher;
    private VehicleRepository vehicleRepository;


    @Override
    public VehicleUseCategoryId placeVehicleOrder(VehicleUseOrderForm vehicleUseForm) {
        Objects.requireNonNull(vehicleUseForm," vehicle order must not be null.");
        var constraintViolations = validator.validate(vehicleUseForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The vehicle order form is not valid", constraintViolations);
        }
        var newOrder = vehicleUseCategoryRepository.saveAndFlush(toDomainObject(vehicleUseForm));
        newOrder.getVehicleList().forEach(item->domainEventPublisher.publish(new VehicleItemCreated(item.getUsername(),item.getVehiclePrice())));
        return newOrder.getId();
    }


    private VehicleUseCategory toDomainObject(VehicleUseOrderForm vehicleUseForm) {
        var order = new VehicleUseCategory(Instant.now(),vehicleUseForm.getCurrency(),vehicleUseForm.getCategoryType());
        vehicleUseForm.getItems().forEach(item->order.addItem(item.getVehicle(),item.getQuantity()));
        return order;
    }

    @Override
    public List<VehicleUseCategory> findAll() {
        return this.vehicleUseCategoryRepository.findAll();
    }

    @Override
    public List<Vehicle> findAllVehicles() {
       return this.vehicleRepository.findAll();
    }

    @Override
    public Optional<VehicleUseCategory> findById(VehicleUseCategoryId id) {
        return this.vehicleUseCategoryRepository.findById(id);
    }

    @Override
    public void addItem(VehicleUseCategoryId vehicleUseCategoryOrderId, VehicleForm VehicleForm) throws VehicleUseCategoryIdNotExistException {
        VehicleUseCategory vehicleorder = vehicleUseCategoryRepository.findById(vehicleUseCategoryOrderId).orElseThrow(VehicleUseCategoryIdNotExistException::new);
        vehicleorder.addItem(VehicleForm.getVehicle(),VehicleForm.getQuantity());
        vehicleUseCategoryRepository.saveAndFlush(vehicleorder);
        domainEventPublisher.publish(new VehicleItemCreated(VehicleForm.getVehicle().getUsername(),VehicleForm.getVehicle().getVehiclePrice()));
    }

    @Override
    public void deleteItem(VehicleUseCategoryId vehicleUseCategoryOrderId, VehicleId vehicleId) throws VehicleUseCategoryIdNotExistException, VehicleIdNotExistException {
        VehicleUseCategory order = vehicleUseCategoryRepository.findById(vehicleUseCategoryOrderId).orElseThrow(VehicleUseCategoryIdNotExistException::new);
        order.removeItem(vehicleId);
        vehicleUseCategoryRepository.saveAndFlush(order);
    }

    @Override
    public Vehicle save(VehicleDto vehicleDto) {
        return this.vehicleRepository.save(new Vehicle(vehicleDto.getUsername(),vehicleDto.getVehiclePrice(),vehicleDto.getQuantity(),vehicleDto.getBasicInformation()));

    }

    @Override
    public void deleteVehicleById(VehicleId of) {
        this.vehicleRepository.deleteById(of);
    }

    @Override
    public Optional<Vehicle> findVehicleById(VehicleId of) {
        return this.vehicleRepository.findById(of);
    }
}
