package com.se196693.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se196693.mvc.entity.ProjectMember;
import com.se196693.mvc.enums.ProjectRole;

import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

    int countByProjectIdAndProjectRole(Long projectId, ProjectRole role);

    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);
}
