package com.demo.emt.carscatalog.domain.model;

import com.demo.emt.carscatalog.domain.model.ids.VehicleId;
import com.demo.emt.carscatalog.domain.model.ids.VehicleUseCategoryId;
import com.demo.emt.sharedkernel.domain.base.AbstractEntity;
import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.sharedkernel.domain.financial.Money;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
public class VehicleUseCategory extends AbstractEntity<VehicleUseCategoryId> {

    private Instant vehicleOrderOn;

    @Enumerated(EnumType.STRING)
    private VehicleCategoryState categoryState;

    private VehicleCategoryType categoryType;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Vehicle> vehicleList = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Currency currency;

    public VehicleUseCategory() {
        super(VehicleUseCategoryId.randomId(VehicleUseCategoryId.class));
    }
    public VehicleUseCategory(Instant now,Currency currency,VehicleCategoryType categoryType) {
        super(VehicleUseCategoryId.randomId(VehicleUseCategoryId.class));
        this.vehicleOrderOn= now;
        this.currency=currency;
        this.categoryType = categoryType;
    }



    public Money total() {
        return vehicleList.stream().map(Vehicle::subtotal).reduce(new Money(currency, 0.0), Money::add);
    }

    public Vehicle addItem(@NonNull Vehicle vehicle, int qty) {
        Objects.requireNonNull( vehicle,"vehicle must not be null");
        var item  = new Vehicle(vehicle.getUsername(),vehicle.getVehiclePrice(),qty,vehicle.getBasicInformation());
        vehicleList.add(item);
        return item;
    }

    public void removeItem(@NonNull VehicleId vehicleId) {
        Objects.requireNonNull(vehicleId,"Vehicle id  must not be null");
        vehicleList.removeIf(v->v.getId().equals(vehicleId));
    }
}
