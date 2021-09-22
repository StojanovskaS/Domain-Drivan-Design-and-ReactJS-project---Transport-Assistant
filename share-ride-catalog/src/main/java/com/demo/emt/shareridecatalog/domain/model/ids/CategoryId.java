package com.demo.emt.shareridecatalog.domain.model.ids;

import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class CategoryId extends DomainObjectId {
    private CategoryId() {
        super(CategoryId.randomId(CategoryId.class).getId());
    }

    public CategoryId(@NonNull String uuid) {
        super(uuid);
    }

    public static CategoryId of(String uuid) {
        CategoryId cid = new CategoryId(uuid);
        return cid;
    }
}
