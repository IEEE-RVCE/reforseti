package org.ieeervce.api.siterearnouveau.dto.execom;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExecomMemberDTO {
    private Integer societyId;
    private String firstName;
    private String lastName;
    private String position;
    private String imagePath;
    private LocalDate tenureStartDate;
    private LocalDate tenureEndDate;
}
