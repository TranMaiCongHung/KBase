package com.se196693.mvc.service.impl;

import com.se196693.mvc.dto.response.FileDownload;
import com.se196693.mvc.dto.response.FileResponse;
import com.se196693.mvc.entity.File;
import com.se196693.mvc.entity.Folder;
import com.se196693.mvc.exception.ResourceNotFoundException;
import com.se196693.mvc.repository.FileRepository;
import com.se196693.mvc.repository.FolderRepository;
import com.se196693.mvc.service.FileService;
import com.se196693.mvc.service.R2StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final R2StorageService r2StorageService; // Tích hợp Service chuyên xử lý Cloud

    @Value("${r2.public-url:}")
    private String publicUrl;


    @Override
    @Transactional
    public FileResponse uploadFile(Long folderId, MultipartFile file) throws IOException {
        Folder folder = getValidFolder(folderId);

        String objectKey = r2StorageService.uploadFile(file);

        File savedFile = saveFileMetadataToDatabase(file, objectKey, folder);

        return buildFileResponse(savedFile);
    }

    @Override
    public FileDownload downloadFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        ResponseInputStream<GetObjectResponse> s3Object = r2StorageService.downloadFile(file.getObjectKey());

        return new FileDownload(
                new InputStreamResource(s3Object),
                file.getContentType(),
                file.getFileName()
        );
    }
    @Override
    public List<FileResponse> getFilesByFolder(Long folderId) {
        getValidFolder(folderId);

        List<File> files = fileRepository.findByFolderId(folderId);

        return files.stream()
                .map(this::buildFileResponse)
                .toList();
    }
    @Override
    @Transactional
    public void deleteFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        r2StorageService.deleteFile(file.getObjectKey());

        fileRepository.delete(file);
    }

    private Folder getValidFolder(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new ResourceNotFoundException("Folder not found with id: " + folderId));
    }

    private File saveFileMetadataToDatabase(MultipartFile file, String objectKey, Folder folder) {
        File newFile = File.builder()
                .fileName(file.getOriginalFilename())
                .objectKey(objectKey)
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .folder(folder)
                .build();
        return fileRepository.save(newFile);
    }

    private FileResponse buildFileResponse(File file) {
        String downloadUrl = (publicUrl != null && !publicUrl.isEmpty())
                ? publicUrl + "/" + file.getObjectKey()
                : "/api/files/" + file.getId() + "/download";

        return FileResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .contentType(file.getContentType())
                .fileUrl(downloadUrl)
                .build();
    }
}
