package com.Uber.UberApp.repository;

import com.Uber.UberApp.model.DriverElasticDocument;
import com.Uber.UberApp.model.PassengerElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverDocumentRepository extends ElasticsearchRepository<DriverElasticDocument,String> {

    List<DriverElasticDocument> findByNameContainingOrEmailContainingOrLicenseNumberContaining(String name, String email,String licenseNumber);
}
