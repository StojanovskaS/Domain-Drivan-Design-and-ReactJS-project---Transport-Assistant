package com.demo.emt.sharedkernel.infra;


import com.demo.emt.sharedkernel.domain.events.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}