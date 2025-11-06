package com.Uber.UberApp.model;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "passengers")
@Builder
public class PassengerElasticDocument {

    @Id
    private String id;

    private String name;

    private String email;


}
