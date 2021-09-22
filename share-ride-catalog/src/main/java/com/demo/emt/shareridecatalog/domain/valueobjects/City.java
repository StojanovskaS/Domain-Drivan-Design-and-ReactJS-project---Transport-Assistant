package com.demo.emt.shareridecatalog.domain.valueobjects;

import com.demo.emt.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class City implements ValueObject {
    String CityName;

    public City(String cityName) {
        this.CityName = cityName;
    }
    public City(){
        this.CityName="";
    }
}
