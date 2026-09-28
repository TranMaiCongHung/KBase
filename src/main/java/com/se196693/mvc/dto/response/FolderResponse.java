package com.se196693.mvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FolderResponse {
    private Long id;
    private String name;
    private Long projectId;
}
