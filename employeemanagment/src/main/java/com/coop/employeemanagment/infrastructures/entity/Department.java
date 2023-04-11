package com.coop.employeemanagment.infrastructures.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(targetEntity = Employee.class, cascade = CascadeType.MERGE)
    private Employee departmentManager;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "emp_workIn_depts",
            joinColumns = { @JoinColumn(name = "dept_id") },
            inverseJoinColumns = { @JoinColumn(name = "emp_id") })
    @Column(insertable=false)
    private Set<Employee> employees;

    public void setDepartmentManager(Employee manager) {
        this.departmentManager = manager;
    }
}

