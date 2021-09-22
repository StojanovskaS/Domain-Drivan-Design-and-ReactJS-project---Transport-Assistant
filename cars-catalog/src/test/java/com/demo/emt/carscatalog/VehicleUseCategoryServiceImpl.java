package com.demo.emt.carscatalog;

import com.demo.emt.carscatalog.domain.exceptions.VehicleUseCategoryIdNotExistException;
import com.demo.emt.carscatalog.domain.model.Vehicle;
import com.demo.emt.carscatalog.domain.model.VehicleUseCategory;
import com.demo.emt.carscatalog.domain.model.ids.VehicleId;
import com.demo.emt.carscatalog.domain.model.ids.VehicleUseCategoryId;
import com.demo.emt.carscatalog.domain.valueobjects.SiteUser;
import com.demo.emt.carscatalog.service.VehicleUseCategoryService;
import com.demo.emt.carscatalog.service.forms.VehicleForm;
import com.demo.emt.carscatalog.service.forms.VehicleUseOrderForm;
import com.demo.emt.carscatalog.xport.client.SiteUserClient;
import com.demo.emt.sharedkernel.domain.financial.Currency;
import com.demo.emt.sharedkernel.domain.financial.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class VehicleUseCategoryServiceImpl {
    @Autowired
    private VehicleUseCategoryService orderService;

    @Autowired
    private SiteUserClient siteUserClient;

    private static Vehicle newVehicle(String username,Money vehiclePrice, String basicInformation) {
        Vehicle p = new Vehicle(username, vehiclePrice,0, basicInformation);
        return p;
    }

    @Test
    public void testPlaceOrder() {

        VehicleForm oi1 = new VehicleForm();
        oi1.setVehicle(newVehicle("admin",Money.valueOf(Currency.MKD,500.0),"bla"));
        oi1.setQuantity(1);

        VehicleForm oi2 = new VehicleForm();
        oi2.setVehicle(newVehicle("admin2",Money.valueOf(Currency.MKD,400.0),"bla"));
        oi2.setQuantity(2);

        VehicleUseOrderForm orderForm = new VehicleUseOrderForm();
        orderForm.setCurrency(Currency.MKD);
        orderForm.setItems(Arrays.asList(oi1,oi2));

        VehicleUseCategoryId newOrderId = orderService.placeVehicleOrder(orderForm);
        VehicleUseCategory newOrder = orderService.findById(newOrderId).orElseThrow(VehicleUseCategoryIdNotExistException::new);
        Assertions.assertEquals(newOrder.total(),Money.valueOf(Currency.MKD,1300.0));

    }

    @Test
    public void testGetAllUsersRealData() {
        List<SiteUser> siteUsers = siteUserClient.findAll();
        SiteUser p1 = siteUsers.get(0);

        VehicleForm oi1 = new VehicleForm();
        oi1.setVehicle(newVehicle(p1.getUsername(),Money.valueOf(Currency.MKD,500.0),"bla"));
        oi1.setQuantity(1);

        VehicleUseOrderForm orderForm = new VehicleUseOrderForm();
        orderForm.setCurrency(Currency.MKD);
        orderForm.setItems(Arrays.asList(oi1));


        VehicleUseCategoryId newOrderId = orderService.placeVehicleOrder(orderForm);
        VehicleUseCategory newOrder = orderService.findById(newOrderId).orElseThrow(VehicleUseCategoryIdNotExistException::new);
        Assertions.assertEquals(newOrder.total(),Money.valueOf(Currency.MKD,1800.0));

    }

}
