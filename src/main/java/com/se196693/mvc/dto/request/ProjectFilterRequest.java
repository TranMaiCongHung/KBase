package com.se196693.mvc.dto.request;

import com.se196693.mvc.enums.ProjectRole;
import lombok.Data;

@Data
public class ProjectFilterRequest {
    private String keyword;
    private ProjectRole role;
}
