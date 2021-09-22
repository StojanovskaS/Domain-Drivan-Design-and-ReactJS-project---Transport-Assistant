package com.demo.emt.carscatalog.domain.model.ids;

import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class VehicleUseCategoryId extends DomainObjectId {
    private VehicleUseCategoryId() {
        super(VehicleUseCategoryId.randomId(VehicleUseCategoryId.class).getId());
    }

    public VehicleUseCategoryId(@NonNull String uuid) {
        super(uuid);
    }

    public static VehicleUseCategoryId of(String uuid) {
        VehicleUseCategoryId sid = new VehicleUseCategoryId(uuid);
        return sid;
    }
}
