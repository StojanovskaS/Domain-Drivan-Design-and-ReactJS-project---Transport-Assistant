package com.demo.emt.sharedkernel.domain.events.vehicle;

import com.demo.emt.sharedkernel.domain.config.TopicHolder;
import com.demo.emt.sharedkernel.domain.events.DomainEvent;
import com.demo.emt.sharedkernel.domain.financial.Money;
import lombok.Getter;

@Getter
public class VehicleItemCreated extends DomainEvent {

    private String siteUserID;
    private Money price;

    public VehicleItemCreated(String topic) {
        super(TopicHolder.TOPIC_VEHICLE_ORDER_ITEM_CREATED);
    }

    public VehicleItemCreated(String siteUserID, Money price) {
        super(TopicHolder.TOPIC_VEHICLE_ORDER_ITEM_CREATED);
        this.siteUserID = siteUserID;
        this.price = price;
    }
}
