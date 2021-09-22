package com.demo.emt.carscatalog.domain.repository;

import com.demo.emt.carscatalog.domain.model.Vehicle;
import com.demo.emt.carscatalog.domain.model.ids.VehicleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, VehicleId> {
}
