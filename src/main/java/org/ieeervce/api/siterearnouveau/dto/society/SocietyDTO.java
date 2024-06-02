package org.ieeervce.api.siterearnouveau.dto.society;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocietyDTO {
    private String societyName;
    private String vision;
    private String mission;
    private String descriptionText;
    private short referenceId;
    private boolean affinity;
}
