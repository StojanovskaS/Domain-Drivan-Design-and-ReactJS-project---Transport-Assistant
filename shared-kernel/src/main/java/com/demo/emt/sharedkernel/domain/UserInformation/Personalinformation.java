package com.demo.emt.sharedkernel.domain.UserInformation;

import com.demo.emt.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Personalinformation implements ValueObject {
    String firstname;
    String lastname;
    String email;

    public Personalinformation(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    public Personalinformation(){
        this.firstname="";
        this.lastname="";
        this.email="";
    }
}
