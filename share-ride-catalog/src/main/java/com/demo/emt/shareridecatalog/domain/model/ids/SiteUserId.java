package com.demo.emt.shareridecatalog.domain.model.ids;


import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class SiteUserId extends DomainObjectId {

    private SiteUserId() {
        super(SiteUserId.randomId(SiteUserId.class).getId());
    }

    public SiteUserId(@NonNull String uuid) {
        super(uuid);
    }

    public static SiteUserId of(String uuid) {
        SiteUserId sid = new SiteUserId(uuid);
        return sid;
    }

}