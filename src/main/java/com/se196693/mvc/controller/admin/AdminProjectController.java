package com.se196693.mvc.controller.admin;

import com.se196693.mvc.dto.request.ProjectFilterRequest;
import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/projects")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectResponse>>> getAllProjects(
            @ParameterObject @ModelAttribute ProjectFilterRequest filter,
            @ParameterObject @RequestParam(defaultValue = "0") int page,
            @ParameterObject @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Listed all projects successfully",
                        projectService.getAllProjectsForAdmin(filter, page, size)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.deleteProjectForAdmin(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Deleted project successfully",
                        null
                )
        );
    }
}
