package org.ieeervce.api.siterearnouveau.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

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
    int eventId;

    @Column(name = "ename")
    String eventName;

    @Column(name = "eventstart")
    LocalDateTime eventStartTime;

    @Column(name = "eventend")
    LocalDateTime eventEndTime;

    @Column(name = "pubstart")
    LocalDateTime publicityStartTime;

    @Column(name = "pubend")
    LocalDateTime publicityEndTime;

    @Column(name = "feeno")
    Integer feeNotIEEEMember;

    @Column(name = "feeyes")
    Integer feeIEEEMember;

    @Column(name = "ecat")
    Integer eventCategory;

    @Column
    String smallposterlink;
    @Column
    String largeposterlink;
    @Column
    String reglink;
    @Column
    String brochurelink;
    @Column
    @JdbcTypeCode(value = SqlTypes.JSON)
    JsonNode hosts;
    @Column
    String keywords;
}
