package com.demo.emt.carscatalog.domain.repository;

import com.demo.emt.carscatalog.domain.model.VehicleUseCategory;
import com.demo.emt.carscatalog.domain.model.ids.VehicleUseCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleUseCategoryRepository extends JpaRepository<VehicleUseCategory, VehicleUseCategoryId> {
}
