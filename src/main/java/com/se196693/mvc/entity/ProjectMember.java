package com.se196693.mvc.entity;

import com.se196693.mvc.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Entity
@Builder// Builder Pattern, giúp khoi tao object ro rang, kh cần quan tâm đến thứ tự truyền
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_member")
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectRole projectRole;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectMember(ProjectRole projectRole, User user, Project project) {
        this.projectRole = projectRole;
        this.user = user;
        this.project = project;
    }
}
