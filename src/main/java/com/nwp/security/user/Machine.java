package com.nwp.security.user;

import com.nwp.security.user.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate creationDate;

}
