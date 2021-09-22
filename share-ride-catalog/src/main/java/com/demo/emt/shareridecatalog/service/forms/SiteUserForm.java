package com.demo.emt.shareridecatalog.service.forms;

import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.sharedkernel.domain.financial.Money;
import com.demo.emt.shareridecatalog.domain.model.enumerations.Role;
import lombok.Data;

@Data
public class SiteUserForm {
    //or register form

//    String id; // ne sum sigurna za ova
    String username;
    String email;
    String firstname;
    String lastname;
    String password;
    String repeatpassword;
    Role role;
    Money budget;

    public SiteUserForm() {
    }

    public SiteUserForm(String username, String email, String firstname, String lastname, String password, String repeatpassword, Role role,Double cena, String currency) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.repeatpassword = repeatpassword;
        this.role = role;
        this.budget= Money.valueOf(Currency.valueOf(currency),cena);
    }
    public SiteUserForm(String username,String password){
        this.username=username;
        this.password=password;
    }
}
