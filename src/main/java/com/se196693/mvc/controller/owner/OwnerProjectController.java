package com.se196693.mvc.controller.owner;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owner/projects")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
public class OwnerProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@RequestBody ProjectCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        "Created successfully",
                        projectService.createProject(request)
                )
        );
    }
}
