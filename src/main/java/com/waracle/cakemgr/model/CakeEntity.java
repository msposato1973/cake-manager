package com.waracle.cakemgr.model;

import jakarta.persistence.*;


import java.io.Serializable;
import lombok.*;
@Entity
@Table(name = "cake")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CakeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String title;

    @Column(name = "first_name")
    private String description;

    @Column(name = "last_name")
    private String image;



}
