package com.demo.emt.shareridecatalog.service.forms;

import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.model.Category;
import com.demo.emt.shareridecatalog.domain.model.Post;
import com.demo.emt.shareridecatalog.domain.model.PostInterest;
import com.demo.emt.shareridecatalog.domain.model.SiteUser;
import com.demo.emt.shareridecatalog.domain.model.enumerations.CategoryName;
import com.demo.emt.shareridecatalog.domain.model.ids.CategoryId;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import lombok.Data;

import java.util.GregorianCalendar;

@Data
public class PostForm {
//    Post post;
    //ne sum sigurna za ovoj del
    String user;
    City odGrad;
    Money cena;
    String opis;
    GregorianCalendar den;
    CategoryId category;

    public PostForm(String user, String odGrad, String currency,Double cena, String opis, GregorianCalendar den, CategoryId category) {
        this.user = user;
        this.odGrad = new City(odGrad);
        this.cena = new Money(Currency.valueOf(currency),cena);
        this.opis = opis;
        this.den = den;
        this.category = category;
    }

//    public PostForm(String user, String odGrad, String currency,Double cena, String opis, GregorianCalendar den, String category) {
//        this.user = user;
//        this.odGrad = new City(odGrad);
//        this.cena = new Money(Currency.valueOf(currency),cena);
//        this.opis = opis;
//        this.den = den;
//        this.category = CategoryName.valueOf(category);
//    }
}
