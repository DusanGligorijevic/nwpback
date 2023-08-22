package com.nwp.security.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    private Machine machine;

}
