package com.coop.employeemanagment.infrastructures.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account",
        uniqueConstraints =
                {@UniqueConstraint(name = "username_unique", columnNames = "username")})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "account")
    @JsonBackReference
    private Employee employee;
}
