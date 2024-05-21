package org.ieeervce.api.siterearnouveau.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsernamePasswordDTO {
    private String userId;
    private String password;
}
