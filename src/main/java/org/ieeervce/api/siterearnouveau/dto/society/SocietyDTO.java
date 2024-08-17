package org.ieeervce.api.siterearnouveau.dto.society;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocietyDTO {
    @NotBlank
    private String societyName;
    @NotNull
    private String vision;
    @NotNull
    private String mission;
    private String descriptionText;
    private short referenceId;
    private boolean affinity;
}
