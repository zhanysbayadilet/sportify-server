package kz.sportify.gateway.payload.response;

import lombok.*;

@Data
@Builder
public class RoleResponse {
    String code;
    String kkName;
    String ruName;
    String enName;
}
