package org.ieeervce.api.siterearnouveau.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {
    @NonNull
    private Integer userId;
    @NonNull
    private Integer societyId;
    @NonNull
    private String firstName;

    @NonNull
    private String lastName;
    @Email
    private String email;
    @Length(min = 6, max=127)
    private String password;
    @NonNull
    private String role;
}
