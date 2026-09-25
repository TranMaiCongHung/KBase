package com.se196693.mvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberResponse {
    private Long userId;
    private String username;
    private String email;
    private String projectRole;
}
