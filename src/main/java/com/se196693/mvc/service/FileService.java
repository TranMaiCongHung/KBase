package com.se196693.mvc.service;

import com.se196693.mvc.dto.response.FileDownload;
import com.se196693.mvc.dto.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileResponse uploadFile(Long folderId, MultipartFile file) throws IOException;
    FileDownload downloadFile(Long fileId);
    List<FileResponse> getFilesByFolder(Long folderId);
    void deleteFile(Long fileId);

}
