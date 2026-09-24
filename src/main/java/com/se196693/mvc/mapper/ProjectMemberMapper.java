package com.se196693.mvc.mapper;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.request.ProjectMemberRequest;
import com.se196693.mvc.dto.response.ProjectMemberResponse;
import com.se196693.mvc.entity.Project;
import com.se196693.mvc.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {
    ProjectMember toEntity(ProjectMemberRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    ProjectMemberResponse toResponse(ProjectMember projectMember);
}
