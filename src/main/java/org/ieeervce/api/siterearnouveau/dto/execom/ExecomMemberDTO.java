package org.ieeervce.api.siterearnouveau.dto.execom;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExecomMemberDTO {
    private Integer societyId;
    @NotBlank
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String position;
    private String imagePath;
    @NotNull
    private LocalDate tenureStartDate;
    private LocalDate tenureEndDate;
}
