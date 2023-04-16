package com.coop.employeemanagment.infrastructures.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name ="employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "grand_father_name")
    private String grandFatherName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "national_id", nullable = false)
    private long nationalID;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @JsonIgnore
    @Column(columnDefinition = "boolean default false")
    private boolean isActive = false;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    @Fetch(FetchMode.JOIN)
    private Role role;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Account account;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {CascadeType.ALL,CascadeType.MERGE},
            mappedBy = "employees")
    @JsonBackReference
    private Set<Department> department;


}
