package com.Uber.UberApp.model;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "drivers")
@Data
@Builder
public class DriverElasticDocument {


    @Id
    private String id;
    private String name;
    private String email;
    private String licenseNumber;
}
