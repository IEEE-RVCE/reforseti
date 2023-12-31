package org.ieeervce.api.siterearnouveau.dto.execom;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExecomMemberDTO {
    Integer societyId;
    String firstName;
    String lastName;
    String position;
    String imagePath;
    LocalDate tenureStartDate;
    LocalDate tenureEndDate;
}
