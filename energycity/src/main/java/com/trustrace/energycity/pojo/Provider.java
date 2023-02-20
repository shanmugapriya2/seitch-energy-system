package com.trustrace.energycity.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "Providers")
public class Provider {
    @Id
    private String id;
    @NotEmpty
    @Size(min = 3,max = 15,message = "must contain min 3 characters")
    private String name;
    @Field("amount_per_kwh")
    private Double amountPerKwh;

    private String status;

}

