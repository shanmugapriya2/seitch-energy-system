package com.trustrace.energycity.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLogin {
    @Email(regexp= "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "Enter a valid email id")
    @NotEmpty
    @Field("email_id")
    private String emailId;
    @NotEmpty
    private String password;
}
