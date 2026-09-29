package com.se196693.mvc.controller.user;

import com.se196693.mvc.dto.request.ProjectMemberRequest;
import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.ProjectMemberResponse;
import com.se196693.mvc.service.ProjectMemberService;
import com.se196693.mvc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> addMemberToProject(
            @PathVariable("projectId") Long projectId,
            @RequestBody ProjectMemberRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Added member successfully",
                        projectMemberService.addMemberToProject(projectId, request)
                )
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeMemberFromProject(@PathVariable(name = "projectId") Long projectId,
                                                                     @PathVariable(name = "userId") Long userId) {
        projectMemberService.removeMemberFromProject(projectId, userId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Removed member successfully",
                        null
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> getMembers(@PathVariable Long projectId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch successfully",
                        projectMemberService.getMembers(projectId)
                )
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> updateMember(
            @PathVariable(name = "projectId") Long projectId,
            @RequestBody ProjectMemberRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch successfully",
                        projectMemberService.updateRoleForMember(projectId, request)
                )
        );
    }
}
