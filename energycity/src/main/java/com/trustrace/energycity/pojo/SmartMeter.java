package com.trustrace.energycity.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "Smart_meters")
public class SmartMeter {
    @Id
    private String id;
    @NotEmpty(message = "Enter meter id")
    @Field("meter_id")
    private String meterId;
    @Field("provider_id")
    @NotEmpty(message = "Enter provider id")
    private String providerId;
    private String status;
    private List<Reading> readings;

}

