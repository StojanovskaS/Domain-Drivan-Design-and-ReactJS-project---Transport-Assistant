package com.demo.emt.sharedkernel.domain.events.vehicle;

import com.demo.emt.sharedkernel.domain.config.TopicHolder;
import com.demo.emt.sharedkernel.domain.events.DomainEvent;
import com.demo.emt.sharedkernel.domain.financial.Money;
import lombok.Getter;

@Getter
public class VehicleItemRemoved extends DomainEvent {

    private String siteuserID;
    private Money price;

    public VehicleItemRemoved(String topic) {
        super(TopicHolder.TOPIC_VEHICLE_ORDER_REMOVED);
    }

    public VehicleItemRemoved(String topic, String siteuserID, Money price) {
        super(TopicHolder.TOPIC_VEHICLE_ORDER_REMOVED);
        this.siteuserID = siteuserID;
        this.price = price;
    }
}