package org.ieeervce.api.siterearnouveau.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Column(name = "eid")
    @Id
    private int eventId;

    @Column(name = "ename")
    private String eventName;

    @Column(name = "eventstart")
    private LocalDateTime eventStartTime;

    @Column(name = "eventend")
    private LocalDateTime eventEndTime;

    @Column(name = "pubstart")
    private LocalDateTime publicityStartTime;

    @Column(name = "pubend")
    private LocalDateTime publicityEndTime;

    @Column(name = "feeno")
    private Integer feeNotIEEEMember;

    @Column(name = "feeyes")
    private Integer feeIEEEMember;

    @Column(name = "ecat")
    private Integer eventCategory;

    @Column
    private String smallposterlink;
    @Column
    private String largeposterlink;
    @Column
    private String reglink;
    @Column
    private String brochurelink;
    @Column
    @JdbcTypeCode(value = SqlTypes.JSON)
    private List<Host> hosts;
    @Column
    private String keywords;

    @Data
    public static class Host {
        private String name;
        /**
         * Picture link
         */
        private String piclink;
        private String details;
    }
}
