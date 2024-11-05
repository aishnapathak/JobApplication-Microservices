package com.aishna.reviewms.review;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double rating;
    private Long companyId;

}
