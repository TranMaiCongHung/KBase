package com.se196693.mvc.controller.user;

import com.se196693.mvc.dto.request.FolderCreationRequest;
import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.FolderResponse;
import com.se196693.mvc.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @PostMapping("/{projectId}/folders")
    public ResponseEntity<ApiResponse<FolderResponse>> createFolder(@PathVariable Long projectId,
                                                                    @Valid  @RequestBody FolderCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        "Created Successfully",
                        folderService.createFolder(projectId, request)
                )
        );
    }

    @GetMapping("/{projectId}/folders")
    public ResponseEntity<ApiResponse<List<FolderResponse>>> getFolders(@PathVariable Long projectId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch Successfully",
                        folderService.getFolders(projectId)
                )
        );
    }

    @GetMapping("/{projectId}/folders/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponse>> getDetailsFolder(@PathVariable(name = "folderId") Long folderId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch Successfully",
                        folderService.getDetailsFolder(folderId)
                )
        );
    }
    @DeleteMapping("/{projectId}/folders/{folderId}")
    public ResponseEntity<ApiResponse<Void>> deleteFolder(@PathVariable(name = "folderId") Long folderId,
                                                          @PathVariable(name = "projectId") Long projectId) {
        folderService.deleteFolder(projectId,folderId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch Successfully",
                        null
                )
        );
    }

}
