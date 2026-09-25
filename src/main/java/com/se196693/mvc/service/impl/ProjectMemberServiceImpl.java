package com.se196693.mvc.service.impl;

import com.se196693.mvc.dto.request.ProjectMemberRequest;
import com.se196693.mvc.dto.response.ProjectMemberResponse;
import com.se196693.mvc.entity.Project;
import com.se196693.mvc.entity.ProjectMember;
import com.se196693.mvc.entity.User;
import com.se196693.mvc.enums.ProjectRole;
import com.se196693.mvc.exception.DuplicateResourceException;
import com.se196693.mvc.exception.InvalidCredentialsException;
import com.se196693.mvc.exception.ResourceNotFoundException;
import com.se196693.mvc.mapper.ProjectMemberMapper;
import com.se196693.mvc.repository.ProjectMemberRepository;
import com.se196693.mvc.service.ProjectMemberService;
import com.se196693.mvc.service.ProjectService;
import com.se196693.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectService projectService;
    private final ProjectMemberMapper projectMemberMapper;
    private final UserService userService;

    @Override
    public ProjectMemberResponse addMemberToProject(Long id, ProjectMemberRequest request) {

        User currentUser = userService.getCurrentUser();
        Project project = projectService.findProjectByIdAndUserAndRole(id, currentUser, ProjectRole.OWNER);
        User user = userService.getUserByEmail(request.getEmail());

        boolean check = projectMemberRepository.existsByUserIdAndProjectId(user.getId(), project.getId());
        if (check) {
            throw new DuplicateResourceException("This user already exists in the current project");
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
        User currentUser = userService.getCurrentUser();
        Project project = projectService.findProjectByIdAndUserAndRole(projectId, currentUser, ProjectRole.OWNER);
        ProjectMember memberToRemove = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found in project"));
        if (memberToRemove.getProjectRole().equals(ProjectRole.OWNER)) {
            int countOwnerRole = projectMemberRepository.countByProjectIdAndProjectRole(projectId, ProjectRole.OWNER);
            if (countOwnerRole <= 1) {
                throw new IllegalArgumentException("Cannot remove the last owner of the project");
            }
        }
        projectMemberRepository.delete(memberToRemove);
    }

}
