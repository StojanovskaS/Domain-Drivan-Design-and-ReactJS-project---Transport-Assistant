package com.demo.emt.carscatalog.service.forms;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.demo.emt.carscatalog.domain.model.VehicleCategoryType;
import com.demo.emt.sharedkernel.domain.financial.Currency;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VehicleUseOrderForm {
    @NotNull
    private Currency currency;
    private VehicleCategoryType categoryType;

    @Valid
    @NotEmpty
    private List<VehicleForm> items = new ArrayList<>();
}
