package com.demo.emt.shareridecatalog.domain.model;


import com.demo.emt.sharedkernel.domain.base.AbstractEntity;
import com.demo.emt.shareridecatalog.domain.model.ids.PostInterestId;
import com.demo.emt.shareridecatalog.domain.model.ids.SiteUserId;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
public class PostInterest extends AbstractEntity<PostInterestId> {

    Integer likesNo;
    @ManyToMany
    List<SiteUser> usersLikes;

    public PostInterest() {
        super(PostInterestId.randomId(PostInterestId.class));
        this.likesNo=0;
        this.usersLikes=new ArrayList<>();

    }

    public PostInterest(Integer likesNo) {
        super(PostInterestId.randomId(PostInterestId.class));
        this.likesNo = likesNo;
        usersLikes=new ArrayList<>();
    }
    public List<String> getUsernamesLikes(){
        List<SiteUser> korisnici=this.usersLikes;
        List<String> imanja=new ArrayList<>();
        for (SiteUser user : korisnici){
            imanja.add(user.getUsername());
            //lista od username da mi vrakja

        }
        return imanja;
    }
}
