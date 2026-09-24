package com.se196693.mvc.repository;

import com.se196693.mvc.entity.Project;
import com.se196693.mvc.entity.User;
import com.se196693.mvc.enums.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project  p JOIN p.projectMembers pm" +
            " WHERE pm.user = :user AND pm.projectRole = :role")
    List<Project> findProjectsByUserAndRole(@Param("user") User user,
                                            @Param("role")ProjectRole role);
}
