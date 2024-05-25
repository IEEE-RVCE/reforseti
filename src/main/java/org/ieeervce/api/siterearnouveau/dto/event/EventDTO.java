package org.ieeervce.api.siterearnouveau.dto.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ieeervce.api.siterearnouveau.entity.Event;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EventDTO {
    private String eventName;
    private LocalDateTime eventStartTime;

    private LocalDateTime eventEndTime;

    private LocalDateTime publicityStartTime;

    private LocalDateTime publicityEndTime;

    private Integer feeNotIEEEMember;

    private Integer feeIEEEMember;

    private Integer eventCategory;

    private String smallposterlink;
    private String largeposterlink;
    private String reglink;
    private String brochurelink;
    private List<Event.Host> hosts;
    private String keywords;
}
