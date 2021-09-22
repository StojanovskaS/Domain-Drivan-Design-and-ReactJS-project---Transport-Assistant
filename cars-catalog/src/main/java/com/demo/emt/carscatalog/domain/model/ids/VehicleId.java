package com.demo.emt.carscatalog.domain.model.ids;

import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class VehicleId extends DomainObjectId {

    private VehicleId() {
        super(VehicleId.randomId(VehicleId.class).getId());
    }

    public VehicleId(@NonNull String uuid) {
        super(uuid);
    }

    public static VehicleId of(String uuid) {
        VehicleId sid = new VehicleId(uuid);
        return sid;
    }

}