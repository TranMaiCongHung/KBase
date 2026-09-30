package com.se196693.mvc.controller.user;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.request.ProjectFilterRequest;
import com.se196693.mvc.dto.request.ProjectMemberRequest;
import com.se196693.mvc.dto.request.ProjectUpdateRequest;
import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.ProjectMemberResponse;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.service.ProjectMemberService;
import com.se196693.mvc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    private final ProjectMemberService projectMemberService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@RequestBody ProjectCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        "Created successfully",
                        projectService.createProject(request)
                )
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@PathVariable Long id,
                                                                      @RequestBody ProjectUpdateRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Updated successfully",
                        projectService.updatedProject(id,request)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id){
        projectService.deletedProject(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Deleted successfully",
                        null
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectByIdAndUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch successfully",
                        projectService.getProjectByIdAndUser(id)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectResponse>>> getProjects(@ParameterObject ProjectFilterRequest request,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Listed successfully",
                        projectService.getMyProjects(request,page, size)
                )
        );
    }
}
