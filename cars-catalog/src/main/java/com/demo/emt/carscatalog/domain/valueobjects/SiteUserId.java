package com.demo.emt.carscatalog.domain.valueobjects;

import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class SiteUserId extends DomainObjectId {

    private SiteUserId() {
        super(SiteUserId.randomId(SiteUserId.class).getId());
    }

    public SiteUserId(String uuid) {
        super(uuid);
    }


}