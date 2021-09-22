package com.demo.emt.shareridecatalog.domain.model;

import com.demo.emt.sharedkernel.domain.UserInformation.Personalinformation;
import com.demo.emt.sharedkernel.domain.base.AbstractEntity;
import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.model.enumerations.Role;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.model.ids.SiteUserId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Getter
public class SiteUser extends AbstractEntity<SiteUserId> {

    String username;
// in Personalinformation: firstname, lastname, email
    Personalinformation personalinformation;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    //ova moze da ne mi treba
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    List<Post> posts;
////    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
//    private Set<Post> posts = new HashSet<>();
//eden user -1 mesto za da gi gleda svoite postovi
    @OneToOne
     MyPostsPart myPostsPart;

    //za delot so car-catalog vo koj sakam da mu se odzemaat pari na korisnik koj ke napravi naracka t.e ke iznajmi ili kupi kola
    private Money budget;


    public SiteUser() {
        super(SiteUserId.randomId(SiteUserId.class));

    }

    public static SiteUser CreateNewUser(String username, String email, String firstname, String lastname, String password, Role role,MyPostsPart myPostsPart,Money  budget) {
        SiteUser siteUser = new SiteUser();
        siteUser.username = username;
        siteUser.personalinformation= new Personalinformation(firstname,lastname,email);
        siteUser.password = password;
        siteUser.role = role;
        siteUser.myPostsPart =myPostsPart;
        siteUser.budget=budget;
        return siteUser;
    }
    // ovoj del e vaka bidejki za post agregat root mi e Category, a  postot e povrzan i treba i da vleze vo mypostpart
    public Post addPostToMyPostPart(@NonNull Post post) {
        Objects.requireNonNull(post,"post must not be null");
        this.myPostsPart.getMyposts().add(post);
        return post;
    }

    public void removePostFromMyPostsPart(@NonNull PostId postId) {
        Objects.requireNonNull(postId,"post must not be null");
        myPostsPart.getMyposts().removeIf(v->v.getId().equals(postId));
    }
//    public SiteUser( SiteUserId id,String username,Money budget,Personalinformation personalinformation){
//
//    }
    public void decreaseBudget(Money price){
        this.budget.subtract(price);
    }
    public void increaseBudget(Money price){
        this.budget.add(price);
    }


}
