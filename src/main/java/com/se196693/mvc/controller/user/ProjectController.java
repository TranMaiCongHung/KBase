package com.se196693.mvc.controller.user;

import com.se196693.mvc.dto.request.ProjectFilterRequest;
import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.PageResponse;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectByIdAndUser(@RequestParam Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch successfully",
                        projectService.getProjectByIdAndUser(id)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectResponse>>> getProjects(ProjectFilterRequest request,
                                                                          @ParameterObject @RequestParam(defaultValue = "0") int page,
                                                                          @ParameterObject @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Listed successfully",
                        projectService.getMyProjects(request,page, size)
                )
        );
    }
}
