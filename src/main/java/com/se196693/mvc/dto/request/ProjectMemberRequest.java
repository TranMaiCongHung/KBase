package com.se196693.mvc.dto.request;

import com.se196693.mvc.enums.ProjectRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberRequest {
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotNull(message = "Role is required")
    private ProjectRole role;
}
