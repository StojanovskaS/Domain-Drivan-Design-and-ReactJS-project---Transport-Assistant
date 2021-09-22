package com.demo.emt.shareridecatalog.domain.model;

import com.demo.emt.sharedkernel.domain.base.AbstractEntity;
import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.model.ids.CategoryId;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "mk_category")
@Getter
public class Category extends AbstractEntity<CategoryId> {
    @Enumerated(EnumType.STRING)
    CategoryName categoryName;
    City grad;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Post> postsList = new ArrayList();

    public Category(CategoryName categoryName,City grad) {
        this.categoryName = categoryName;
        this.grad = grad;
    }
    public Category(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public Category() {
        super(CategoryId.randomId(CategoryId.class));

    }

    public static Category CreateNewCategory(CategoryName categoryName, City grad) {
        Category category = new Category();
        category.categoryName = categoryName;
        category.grad=grad;
        return category;
    }


    public Post addPost(SiteUser user, City odGrad, String opis, Money cena,GregorianCalendar den ,Category category,PostInterest likepart) {
        var item  = new Post( user,odGrad,opis,cena,den,category,likepart);
        postsList.add(item);
        return item;
    }

    public void removePost(@NonNull PostId postId) {
        Objects.requireNonNull(postId,"Post item must not be null");
        postsList.removeIf(v->v.getId().equals(postId));
    }

}
