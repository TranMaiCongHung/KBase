package com.se196693.mvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdateRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}
