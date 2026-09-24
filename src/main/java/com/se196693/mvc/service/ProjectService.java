package com.se196693.mvc.service;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.request.ProjectFilterRequest;
import com.se196693.mvc.dto.request.ProjectUpdateRequest;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.enums.ProjectRole;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectCreateRequest project);

    Page<ProjectResponse> getMyProjects(ProjectFilterRequest request, int page, int size);

    ProjectResponse getProjectByIdAndUser(Long id);

    ProjectResponse updatedProject(Long id, ProjectUpdateRequest request);

    void deletedProject(Long id);
}
