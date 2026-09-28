package com.se196693.mvc.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

public interface R2StorageService {
    String uploadFile(MultipartFile file) throws IOException;
    ResponseInputStream<GetObjectResponse> downloadFile(String objectKey);
    void deleteFile(String objectKey);
}
