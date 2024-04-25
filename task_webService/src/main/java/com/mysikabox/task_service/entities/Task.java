package com.mysikabox.task_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Entity
@Table(name="task")
@AllArgsConstructor
@NoArgsConstructor
public class Task{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Due-Date")
    private LocalDate dueDate;

    @Column(name = "Task-Status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
