package com.se196693.mvc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.se196693.mvc.dto.request.ProjectMemberRequest;
import com.se196693.mvc.dto.response.ProjectMemberResponse;
import com.se196693.mvc.entity.Project;
import com.se196693.mvc.entity.ProjectMember;
import com.se196693.mvc.entity.User;
import com.se196693.mvc.enums.ProjectRole;
import com.se196693.mvc.exception.DuplicateResourceException;
import com.se196693.mvc.exception.ResourceNotFoundException;
import com.se196693.mvc.mapper.ProjectMemberMapper;
import com.se196693.mvc.repository.ProjectMemberRepository;
import com.se196693.mvc.service.ProjectMemberService;
import com.se196693.mvc.service.ProjectService;
import com.se196693.mvc.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectService projectService;
    private final ProjectMemberMapper projectMemberMapper;
    private final UserService userService;

    @Override
    public ProjectMemberResponse addMemberToProject(Long id, ProjectMemberRequest request) {
        Project project = getProjectIfCurrentUserIsOwner(id);
        User user = userService.getUserByEmail(request.getEmail());
        if (projectMemberRepository.existsByUserIdAndProjectId(
                user.getId(), project.getId())) {
            throw new DuplicateResourceException(
                    "This user already exists in the current project"
            );
        }
        ProjectMember projectMember = ProjectMember.builder()
                .projectRole(request.getRole())
                .project(project)
                .user(user)
                .build();
        projectMemberRepository.save(projectMember);

        return projectMemberMapper.toResponse(projectMember);
    }

    @Override
    public void removeMemberFromProject(Long projectId, Long userId) {
        Project project = getProjectIfCurrentUserIsOwner(projectId);
        ProjectMember memberToRemove = getProjectMemberOrThrow(projectId, userId);
        ensureNotRemovingLastOwner(projectId, memberToRemove, null);
        projectMemberRepository.delete(memberToRemove);
    }

    @Override
    public List<ProjectMemberResponse> getMembers(Long projectId) {
        requireCurrentUserIsMember(projectId);
        List<ProjectMember> members = projectMemberRepository.findAllByProjectId(projectId);
        return members.stream().map(projectMemberMapper::toResponse).toList();
    }

    @Override
    public ProjectMemberResponse updateRoleForMember(Long projectId, ProjectMemberRequest request) {
        Project project = getProjectIfCurrentUserIsOwner(projectId);
        User user = userService.getUserByEmail(request.getEmail());
        ProjectMember memberToUpdate = getProjectMemberOrThrow(projectId, user.getId());
        ensureNotRemovingLastOwner(projectId, memberToUpdate, request.getRole());
        memberToUpdate.setProjectRole(request.getRole());
        projectMemberRepository.save(memberToUpdate);
        return projectMemberMapper.toResponse(memberToUpdate);
    }

    private Project getProjectIfCurrentUserIsOwner(Long projectId) {
        User currentUser = userService.getCurrentUser();
        return projectService.findProjectByIdAndUserAndRole(projectId, currentUser, ProjectRole.OWNER);
    }

    private void ensureNotRemovingLastOwner(Long projectId, ProjectMember memberToModify, ProjectRole newRole) {
        if (memberToModify.getProjectRole().equals(ProjectRole.OWNER) && !ProjectRole.OWNER.equals(newRole)) {
            int countOwnerRole = projectMemberRepository.countByProjectIdAndProjectRole(projectId, ProjectRole.OWNER);
            if (countOwnerRole <= 1) {
                throw new IllegalArgumentException("Cannot remove or change role of the last owner of the project");
            }
        }
    }

    private ProjectMember getProjectMemberOrThrow(Long projectId, Long userId) {
        return projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found in project"));
    }

    private void requireCurrentUserIsMember(Long projectId) {
        User currentUser = userService.getCurrentUser();
        if (!projectMemberRepository.existsByUserIdAndProjectId(currentUser.getId(), projectId)) {
            throw new IllegalArgumentException("You do not have permission to view this project");
        }
    }

}
