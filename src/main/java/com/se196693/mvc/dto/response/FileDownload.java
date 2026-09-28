package com.se196693.mvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDownload {
    private InputStreamResource resource; // Chứa dữ liệu luồng của file
    private String contentType;           // Kiểu file (VD: image/png)
    private String fileName;              // Tên file gốc
}
