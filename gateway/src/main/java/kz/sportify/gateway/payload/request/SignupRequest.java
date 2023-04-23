package kz.sportify.gateway.payload.request;

import lombok.*;

import org.springframework.format.annotation.*;
import org.springframework.web.multipart.*;

import java.util.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String name;
    @NotBlank
    @Size(max = 50)
    private String surname;
//    @DateTimeFormat
//    private Date birthDate;
    //    @NumberFormat
//    private Set<String> role;
    private String role;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


}

