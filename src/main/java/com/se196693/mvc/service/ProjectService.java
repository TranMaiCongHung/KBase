package com.se196693.mvc.service;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectCreateRequest project);

    List<ProjectResponse> getProjectsByOwner();
}
