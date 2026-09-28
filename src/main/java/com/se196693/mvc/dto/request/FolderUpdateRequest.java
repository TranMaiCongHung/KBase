package com.se196693.mvc.dto.request;

import jakarta.validation.constraints.NotBlank;

public class FolderUpdateRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
