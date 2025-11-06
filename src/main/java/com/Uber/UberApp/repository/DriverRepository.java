package com.Uber.UberApp.repository;

import com.Uber.UberApp.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.StringReader;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    boolean existsByEmail(String email);
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByLicenseNumber(String licenseNumber);

}
