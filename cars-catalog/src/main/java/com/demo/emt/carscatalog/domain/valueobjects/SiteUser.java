package com.demo.emt.carscatalog.domain.valueobjects;

import com.demo.emt.sharedkernel.domain.UserInformation.Personalinformation;
import com.demo.emt.sharedkernel.domain.base.ValueObject;
import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.sharedkernel.domain.financial.Money;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
@Getter
public class SiteUser implements ValueObject {
        private final SiteUserId id;
        private final String username;
        private final Money budget;
    // in Personalinformation: firstname, lastname, email
        private final Personalinformation personalinformation;


    private SiteUser() {
        this.id=SiteUserId.randomId(SiteUserId.class);
        this.username= "";
        this.budget = Money.valueOf(Currency.MKD,0.0);
        this.personalinformation= new Personalinformation("","","");
    }

    @JsonCreator
    public SiteUser(@JsonProperty("id") SiteUserId id,
                   @JsonProperty("username") String username,
                   @JsonProperty("budget") Money budget,
                   @JsonProperty("personalinformation") Personalinformation personalinformation) {
        this.id = id;
        this.username = username;
        this.budget = budget;
        this.personalinformation = personalinformation;
    }
}
