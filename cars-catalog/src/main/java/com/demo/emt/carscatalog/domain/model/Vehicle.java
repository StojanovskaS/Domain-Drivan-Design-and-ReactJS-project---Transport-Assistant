package com.demo.emt.carscatalog.domain.model;

import com.demo.emt.carscatalog.domain.model.ids.VehicleId;
import com.demo.emt.carscatalog.domain.valueobjects.SiteUserId;
import com.demo.emt.sharedkernel.domain.base.AbstractEntity;
import com.demo.emt.sharedkernel.domain.base.DomainObjectId;
import com.demo.emt.sharedkernel.domain.financial.Money;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.NonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Getter
public class Vehicle extends AbstractEntity<VehicleId> {
    //ovoj del mi e zamislen da bide kako stavka za kupuvanje/iznajmuvanje koja ke moze korisnik da ja kupi zatoa ke imam value object SiteUser i ovde
    private String basicInformation;
    private Money vehiclePrice;
    private int quantity; //ova go dodadov bidejki vo edna kategorija na vozila moze da imame poveke isti modeli na  vozila za ista namena

    @AttributeOverride(name = "username", column = @Column(name = "user_id", nullable = false))
    private  String username;

    public Vehicle() {
        super(DomainObjectId.randomId(VehicleId.class));

    }


    public Vehicle(@NonNull String username, @NonNull Money vehiclePrice, @NonNull int quantity, String basicInformation) {
        super(DomainObjectId.randomId(VehicleId.class));
        this.username = username;
        this.vehiclePrice = vehiclePrice;
        this.basicInformation = basicInformation;
        this.quantity=quantity;
    }




    public Money subtotal() {
        return vehiclePrice.multiply(quantity);
    }
}
