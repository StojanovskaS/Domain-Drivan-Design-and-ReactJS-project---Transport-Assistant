package com.demo.emt.shareridecatalog.domain.model.ids;

import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class PostInterestId extends DomainObjectId {
    private PostInterestId() {
        super(PostInterestId.randomId(PostInterestId.class).getId());
    }

    public PostInterestId(@NonNull String uuid) {
        super(uuid);
    }

    public static PostInterestId of(String uuid) {
        PostInterestId pid = new PostInterestId(uuid);
        return pid;
    }
}