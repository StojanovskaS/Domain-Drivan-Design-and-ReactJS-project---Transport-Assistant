package com.demo.emt.shareridecatalog.domain.model;

import com.demo.emt.sharedkernel.domain.base.AbstractEntity;
import com.demo.emt.shareridecatalog.domain.model.enumerations.MyPostsCardStatus;
import com.demo.emt.shareridecatalog.domain.model.ids.MyPostsPartId;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
public class MyPostsPart extends AbstractEntity<MyPostsPartId> {
//    @OneToOne
//    private SiteUser user;
    //bese ManyToOne no toa e edna klasa vo koja ima poveke postovi
    @ManyToMany
    private List<Post> myposts;

    private MyPostsCardStatus status;

    public MyPostsPart() {
        super(MyPostsPartId.randomId(MyPostsPartId.class));
    }
    public static MyPostsPart CreateNewMyPostPart() {
        MyPostsPart myPostsPart = new MyPostsPart();
        myPostsPart.status = MyPostsCardStatus.CREATED;
        myPostsPart.myposts=new ArrayList<Post>();
        return myPostsPart;
    }

//    public MyPostsPart(SiteUser user, List<Post> myposts, MyPostsCardStatus status) {
//        this.user = user;
//        this.myposts = myposts;
//        this.status = status;
//    }

//    public MyPostsPart(SiteUser user) {
//
//
//        this.status = MyPostsCardStatus.CREATED;
//        this.myposts=new ArrayList<>();
//    }
}
