package com.se196693.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.se196693.mvc.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.entity.Project;
import com.se196693.mvc.entity.ProjectMember;
import com.se196693.mvc.entity.User;
import com.se196693.mvc.enums.ProjectRole;
import com.se196693.mvc.exception.ResourceNotFoundException;
import com.se196693.mvc.mapper.ProjectMapper;
import com.se196693.mvc.repository.ProjectRepository;
import com.se196693.mvc.repository.UserRepository;
import com.se196693.mvc.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        User currentUser = userService.findByUsername(currentUsername);

        Project project = projectMapper.toEntity(request);

        if (project.getProjectMembers() == null) {
            project.setProjectMembers(new ArrayList<>());
        }
        ProjectMember ownerMember = ProjectMember.builder()
                .project(project)
                .user(currentUser)
                .projectRole(ProjectRole.OWNER)
                .build();

        project.getProjectMembers().add(ownerMember);
        Project savedProject = projectRepository.save(project);

        return projectMapper.toResponse(savedProject);
    }

    @Override
    public List<ProjectResponse> getProjectsByOwner() {
        List<Project> list = projectRepository.findAll();
        return List.of();
    }
}
