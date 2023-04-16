package com.coop.employeemanagment.infrastructures.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id ; // not auto incremented
    private String name;
    private String description;

}