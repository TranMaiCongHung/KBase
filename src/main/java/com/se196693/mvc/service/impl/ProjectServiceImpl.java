package com.se196693.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.se196693.mvc.dto.request.ProjectFilterRequest;
import com.se196693.mvc.dto.request.ProjectUpdateRequest;
import com.se196693.mvc.service.UserService;
import com.se196693.mvc.specification.ProjectSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

        User currentUser = userService.getCurrentUser();

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
    public Page<ProjectResponse> getMyProjects(ProjectFilterRequest request, int page, int size) {
        User currentUser = userService.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size, Sort.by( "createdAt").descending());

        Specification<Project> spec = Specification.where(ProjectSpecification.isNotDeleted())
                .and(ProjectSpecification.containsUserWithRole(currentUser, request.getRole()))
                .and(ProjectSpecification.hasKeyword(request.getKeyword()));

        Page<Project> projectPage = projectRepository.findAll(spec, pageable);

        return projectPage.map(projectMapper::toResponse);
    }

    @Override
    public ProjectResponse getProjectByIdAndUser(Long id) {
        Project foundProject = projectRepository.findProjectByIdAndUserAndRole(id,
                userService.getCurrentUser(), ProjectRole.OWNER).orElseThrow(
                () -> new ResourceNotFoundException("Project not found with id: " + id)
        );
        return projectMapper.toResponse(foundProject);
    }

    @Override
    public ProjectResponse updatedProject(Long id, ProjectUpdateRequest request) {

        Project foundProject = findProjectByIdAndUserAndRole(id, userService.getCurrentUser(), ProjectRole.OWNER);

        foundProject.setName(request.getName());
        foundProject.setDescription(request.getDescription());
        projectRepository.save(foundProject);
        return projectMapper.toResponse(foundProject);
    }

    @Override
    public void deletedProject(Long id) {
        Project foundProject = findProjectByIdAndUserAndRole(id, userService.getCurrentUser(), ProjectRole.OWNER);

        foundProject.setDeleted(true);
        projectRepository.save(foundProject);
    }

    @Override
    public Project findProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Project not found with id: " + id)
        );
        return project;
    }
    @Override
    public Project findProjectByIdAndUserAndRole(Long id, User user, ProjectRole role) {
        Project project = projectRepository.findProjectByIdAndUserAndRole(id, user, role).orElseThrow(
                () -> new ResourceNotFoundException("Project not found with id: " + id)
        );
        return project;
    }
}
