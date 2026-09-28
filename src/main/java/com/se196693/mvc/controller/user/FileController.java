package com.se196693.mvc.controller.user;

import com.se196693.mvc.dto.response.ApiResponse;
import com.se196693.mvc.dto.response.FileDownload;
import com.se196693.mvc.dto.response.FileResponse;
import com.se196693.mvc.service.FileService;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/folders/{folderId}/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileResponse>> uploadFile(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Gọi service upload
        FileResponse response = fileService.uploadFile(folderId, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Upload successfully", response)
        );
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {

        // Khai báo kiểu dữ liệu rõ ràng thay vì dùng var
        FileDownload downloadDto = fileService.downloadFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(downloadDto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + downloadDto.getFileName() + "\"")
                .body(downloadDto.getResource());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FileResponse>>> getFilesByFolder(
            @PathVariable Long projectId,
            @PathVariable Long folderId) {

        // Gọi service lấy danh sách file
        List<FileResponse> files = fileService.getFilesByFolder(folderId);

        // Trả về JSON cho Frontend
        return ResponseEntity.ok(
                ApiResponse.success("Fetched files successfully", files)
        );
    }
    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @PathVariable Long fileId) {

        // Gọi service xử lý logic xóa
        fileService.deleteFile(fileId);

        return ResponseEntity.ok(
                ApiResponse.success("Xóa file thành công", null)
        );
    }

}
