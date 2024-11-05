package com.aishna.companyms.company;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;




}
