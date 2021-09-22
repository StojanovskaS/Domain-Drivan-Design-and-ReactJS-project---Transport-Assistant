package com.demo.emt.shareridecatalog.domain.model.ids;

import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class MyPostsPartId extends DomainObjectId {
    private MyPostsPartId() {
        super(MyPostsPartId.randomId(MyPostsPartId.class).getId());
    }

    public MyPostsPartId(@NonNull String uuid) {
        super(uuid);
    }

    public static MyPostsPartId of(String uuid) {
        MyPostsPartId cid = new MyPostsPartId(uuid);
        return cid;
    }
}