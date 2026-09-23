package com.se196693.mvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder// Builder Pattern, giúp khoi tao object ro rang, kh cần quan tâm đến thứ tự truyền
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    @CreationTimestamp
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMember> projectMembers = new ArrayList<>();
}
