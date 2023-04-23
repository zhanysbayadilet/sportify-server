package kz.sportify.gateway.payload.response;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    private String name;

    private String surname;

    public JwtResponse(String token, Long id, String username, String email, List<String> roles, String name, String surname) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.name = name;
        this.surname = surname;
    }
}
