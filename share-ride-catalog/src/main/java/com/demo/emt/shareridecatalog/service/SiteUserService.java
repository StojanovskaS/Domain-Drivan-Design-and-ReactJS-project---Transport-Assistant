package com.demo.emt.shareridecatalog.service;

import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.model.MyPostsPart;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.enumerations.Role;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.model.ids.SiteUserId;
import com.demo.emt.shareridecatalog.service.forms.NewPostForm;
import com.demo.emt.shareridecatalog.service.forms.PostForm;
import com.demo.emt.shareridecatalog.service.forms.SiteUserForm;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

public interface SiteUserService {
    SiteUser login(String username, String password);
    SiteUser register(SiteUserForm form);
    SiteUser findByUserName(String username);
    List<SiteUser> findAll();

    // bidejki agregat root mi e SiteUser na agregatite SiteUser i MyPostParts zatoa logikata za pristap do myPostpart e ovde
    List<Post> listAllPostsInMyPostsPart(String username);
    MyPostsPart getActiveUserPostsPart(String username);
    void addPostToMyPostsPart(Post postForm);
    void updatePostInMyPostPart(NewPostForm postForm,String username);
    void deletePostFromMyPostsPart(PostId postid, String username);
    PostId findPostIdinMyPostsPart(NewPostForm p);
    public Post findPostByIdinMyPostsPart(PostId id, String username);
    //za messagebrokerot na kafka
// ne prakjam SiteUserID tuku orakjam username zatoa vaka
    SiteUser orderItemCreated(String siteUserId, Money price);
    SiteUser orderItemRemoved(String siteUserId, Money price);
}
