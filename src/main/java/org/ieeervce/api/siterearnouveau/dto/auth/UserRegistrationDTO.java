package org.ieeervce.api.siterearnouveau.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotNull
    @Min(99_999)
    private Integer userId;
    @NotNull
    private Integer societyId;
    @NotBlank
    private String firstName;

    @NotNull
    private String lastName;
    @Email
    @NotBlank
    private String email;
    @Length(min = 6, max=127)
    @NotNull
    private String password;
    @NotBlank
    private String role;
}
