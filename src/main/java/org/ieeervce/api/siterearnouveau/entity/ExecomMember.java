package org.ieeervce.api.siterearnouveau.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "execom_members")
@Data
public class ExecomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sid")
    private Integer societyId;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "position")
    private String position;

    @Column(name = "imagepath")
    private String imagePath;
    
    @Column(name = "tenurestart")
    private LocalDate tenureStartDate;

    @Column(name = "tenureend")
    private LocalDate tenureEndDate;

    @Column(name = "execom_member_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID memberUuid;
}
