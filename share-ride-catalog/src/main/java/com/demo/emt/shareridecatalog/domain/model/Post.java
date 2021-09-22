package com.demo.emt.shareridecatalog.domain.model;

import com.demo.emt.sharedkernel.domain.base.AbstractEntity;
import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.model.ids.PostId;
import com.demo.emt.shareridecatalog.domain.valueobjects.City;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

@Data
@Entity
@Getter
public class Post extends AbstractEntity<PostId> {

    @ManyToOne
    SiteUser user;
    City odGrad;
    Money cena;
    //    opisot e bidejki html da ne e slozen ke gi ima samo gradovite vo Makedonija ,a na primer moze da e krajna destinacija nekoe selo vo Makedonija
    String opis;
    GregorianCalendar den;
    @ManyToOne
    Category category;
    // se dava krajnata destinacija samo vo koja teriotrija na nasata zemja e, ovoj del e za da site operacii na postovi se pravat kako del od KAtegorijata

    @OneToOne
    PostInterest likepart;


    public Post(SiteUser user, City odGrad,String opis, Money cena,GregorianCalendar den, Category category,PostInterest likepart) {
        this.user = user;
        this.opis=opis;
        this.category = category;
        this.odGrad = odGrad;
        this.cena = cena;
        this.den=den;
        this.likepart=likepart;
    }

    public Post() {
    }
    public String getData(){
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MMM-yyyy");
        String dateFormatted = formattedDate.format(den.getTime());
        return dateFormatted;
    }
}
