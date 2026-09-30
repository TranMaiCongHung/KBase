package com.se196693.mvc.service;

import com.se196693.mvc.dto.request.ProjectMemberRequest;
import com.se196693.mvc.dto.response.ProjectMemberResponse;

import java.util.List;

public interface ProjectMemberService {
    ProjectMemberResponse addMemberToProject(Long id, ProjectMemberRequest request);

    void removeMemberFromProject(Long projectId, Long userId);

    List<ProjectMemberResponse> getMembers(Long projectId);

    ProjectMemberResponse updateRoleForMember(Long projectId, ProjectMemberRequest request);
}
