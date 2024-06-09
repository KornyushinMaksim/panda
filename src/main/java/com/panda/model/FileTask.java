package com.panda.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file_task")
public class FileTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileToDataBase fileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @ManyToMany
    @JoinTable(name = "file_employee_relation",
                joinColumns = @JoinColumn(name = "file_task_id"),
                inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> executors;

    @Column(name = "dead_line")
    private LocalDate deadLine;
}
