package com.demo.emt.shareridecatalog.xport.events;

import com.demo.emt.sharedkernel.domain.config.TopicHolder;
import com.demo.emt.sharedkernel.domain.events.DomainEvent;
import com.demo.emt.sharedkernel.domain.events.vehicle.VehicleItemRemoved;
import com.demo.emt.shareridecatalog.domain.model.ids.SiteUserId;
import com.demo.emt.shareridecatalog.service.SiteUserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.demo.emt.sharedkernel.domain.events.vehicle.VehicleItemCreated;
@Service
@AllArgsConstructor
public class VehicleEventListener {
    //kafka event listaner t.e preplatnik na topik za koga se kreira unova stavka vo narackata na vozilo da se odzemem od budzetotot na korisnikot
    private final com.demo.emt.shareridecatalog.service.SiteUserService SiteUserService;

    @KafkaListener(topics= TopicHolder.TOPIC_VEHICLE_ORDER_ITEM_CREATED, groupId = "ShareRideCatalog")
    public void consumeOrderItemCreatedEvent(String jsonMessage) {
        try {
            VehicleItemCreated event = DomainEvent.fromJson(jsonMessage,VehicleItemCreated.class);
            SiteUserService.orderItemCreated(event.getSiteUserID(), event.getPrice());
        } catch (Exception e){

        }

    }



    @KafkaListener(topics= TopicHolder.TOPIC_VEHICLE_ORDER_REMOVED, groupId = "ShareRideCatalog")
    public void consumeOrderItemRemovedEvent(String jsonMessage) {
        try {
            VehicleItemRemoved event = DomainEvent.fromJson(jsonMessage,VehicleItemRemoved.class);
            SiteUserService.orderItemRemoved(event.getSiteuserID(), event.getPrice());
        } catch (Exception e){

        }

    }
}
