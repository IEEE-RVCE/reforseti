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
    Integer id;

    @Column(name = "sid")
    Integer societyId;

    @Column(name = "firstname")
    String firstName;

    @Column(name = "lastname")
    String lastName;

    @Column(name = "position")
    String position;

    @Column(name = "imagepath")
    String imagePath;
    
    @Column(name = "tenurestart")
    LocalDate tenureStartDate;

    @Column(name = "tenureend")
    LocalDate tenureEndDate;

    @Column(name = "execom_member_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID memberUuid;
}
