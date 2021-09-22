package com.demo.emt.carscatalog.service.forms;

import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.sharedkernel.domain.financial.Money;
import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;

@Data
public class VehicleDto {
    private String basicInformation;
    private Money vehiclePrice;
    private int quantity; //ova go dodadov bidejki vo edna kategorija na vozila moze da imame poveke isti modeli na  vozila za ista namena
    private  String username;

    public VehicleDto(String basicInformation, Double vehiclePrice, String currency, int quantity, String username) {
        this.basicInformation = basicInformation;
        this.vehiclePrice = Money.valueOf(Currency.valueOf(currency),vehiclePrice);
        this.quantity = quantity;
        this.username = username;
    }
}
