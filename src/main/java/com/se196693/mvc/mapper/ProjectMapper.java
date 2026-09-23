package com.se196693.mvc.mapper;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.response.ProjectMemberResponse;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.entity.Project;
import com.se196693.mvc.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// componentModel = "spring" giúp MapStruct tự đăng ký thành 1 Bean để dùng @Autowired
@Mapper(componentModel = "spring")
public interface ProjectMapper {

    // MapStruct tự động nối tên trùng khớp
    Project toEntity(ProjectCreateRequest request);

    // MapStruct tự động hiểu là phải lấy project.getProjectMembers()
    // và gọi hàm toMemberResponse bên dưới để map cái List đó cho bạn!
    ProjectResponse toResponse(Project project);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    ProjectMemberResponse toMemberResponse(ProjectMember member);
}
