package com.Uber.UberApp.repository;

import com.Uber.UberApp.model.PassengerElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDocumentRepository extends ElasticsearchRepository<PassengerElasticDocument,Long> {

    List<PassengerElasticDocument> findByNameContainingOrEmailContaining(String name,String email);
}
