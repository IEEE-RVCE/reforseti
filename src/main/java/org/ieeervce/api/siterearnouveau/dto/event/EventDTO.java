package org.ieeervce.api.siterearnouveau.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ieeervce.api.siterearnouveau.entity.Event;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EventDTO {
    @NotBlank
    private String eventName;
    @NotNull
    private LocalDateTime eventStartTime;
    @NotNull
    private LocalDateTime eventEndTime;
    @NotNull
    private LocalDateTime publicityStartTime;
    @NotNull
    private LocalDateTime publicityEndTime;
    @NotNull
    private Integer feeNotIEEEMember;
    @NotNull
    private Integer feeIEEEMember;
    @NotNull
    private Integer eventCategory;
    @NotNull
    private String smallposterlink;
    @NotNull
    private String largeposterlink;
    @NotNull
    private String reglink;
    @NotNull
    private String brochurelink;
    private List<Event.Host> hosts;
    @NotNull
    private String keywords;
}
