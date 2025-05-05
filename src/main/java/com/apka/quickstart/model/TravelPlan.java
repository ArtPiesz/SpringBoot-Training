package com.apka.quickstart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String destination;
    private LocalDate startDate;

    private LocalDate endDate;
    private LocalDate createdAt = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
