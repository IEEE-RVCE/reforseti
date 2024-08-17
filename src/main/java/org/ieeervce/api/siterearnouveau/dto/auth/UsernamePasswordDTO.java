package org.ieeervce.api.siterearnouveau.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsernamePasswordDTO {
    @NotBlank
    @Size(min = 6, max = 7)
    private String userId;
    @NotBlank
    @Size(min = 6)
    private String password;
}
