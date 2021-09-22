package com.demo.emt.carscatalog.service.forms;

import com.demo.emt.carscatalog.domain.model.Vehicle;
import com.demo.emt.carscatalog.domain.valueobjects.SiteUser;
import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.sharedkernel.domain.financial.Money;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class VehicleForm {

        @NotNull
        private Vehicle vehicle;

        @Min(1)
        private int quantity = 1;

    public VehicleForm() {
    }

    public VehicleForm(Vehicle vehicle, int quantity) {
        this.vehicle = vehicle;
        this.quantity = quantity;

    }
}
