package com.se196693.mvc.controller.owner;

import com.se196693.mvc.dto.request.ProjectCreateRequest;
import com.se196693.mvc.dto.request.ProjectUpdateRequest;
import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.ProjectResponse;
import com.se196693.mvc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @PutMapping("/id")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@RequestParam Long id,
                                                                      @RequestBody ProjectUpdateRequest request){
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Updated successfully",
                        projectService.updatedProject(id,request)
                )
        );
    }

    @DeleteMapping("/id")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@RequestParam Long id){
        projectService.deletedProject(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Deleted successfully",
                        null
                )
        );
    }
}
