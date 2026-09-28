package com.se196693.mvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FolderCreationRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
