package com.trustrace.energycity.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
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
@Document(collection = "Users")
public class User {
    @Id
    private String id;
    @NotEmpty(message = "Enter user name")
    private String name;
    @Field("email_id")
    @Email(regexp= "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    @NotEmpty
    private String emailId;
    @NotEmpty(message = "Enter valid password")
    @Size(min = 5,message ="min 5 characters")
    private String password;
    @NotEmpty(message = "Enter role of the user")
    private String role;
    private String status;

}
