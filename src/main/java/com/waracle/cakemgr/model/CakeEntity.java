package com.waracle.cakemgr.model;

import jakarta.persistence.*;


import java.io.Serializable;
import lombok.*;
@Entity
@Table(name = "Employee")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CakeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String title;

    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String description;

    @Column(name = "LAST_NAME", nullable = false, length = 300)
    private String image;



}
