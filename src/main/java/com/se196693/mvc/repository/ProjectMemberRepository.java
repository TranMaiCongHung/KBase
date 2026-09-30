package com.se196693.mvc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se196693.mvc.entity.ProjectMember;
import com.se196693.mvc.enums.ProjectRole;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

    int countByProjectIdAndProjectRole(Long projectId, ProjectRole role);

    Optional<ProjectMember> findByProjectIdAndUserId(Long projectId, Long userId);

    List<ProjectMember> findAllByProjectId(Long projectId);
}
