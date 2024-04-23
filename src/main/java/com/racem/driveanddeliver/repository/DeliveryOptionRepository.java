package com.racem.driveanddeliver.repository;

import com.racem.driveanddeliver.entity.DeliveryOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryOptionRepository extends JpaRepository<DeliveryOption, Long> {
    Optional<DeliveryOption> findByMethod(String method);
}
