package com.demo.emt.shareridecatalog.domain.model.ids;

import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class PostId extends DomainObjectId {
    private PostId() {
        super(PostId.randomId(PostId.class).getId());
    }

    public PostId(@NonNull String uuid) {
        super(uuid);
    }

    public static PostId of(String uuid) {
        PostId pid = new PostId(uuid);
        return pid;
    }
}