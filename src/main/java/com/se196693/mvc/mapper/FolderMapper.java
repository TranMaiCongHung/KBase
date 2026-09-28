package com.se196693.mvc.mapper;

import com.se196693.mvc.dto.request.FolderCreationRequest;
import com.se196693.mvc.dto.response.FolderResponse;
import com.se196693.mvc.entity.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FolderMapper {
    Folder toEntity(FolderCreationRequest request);
    @Mapping(source = "project.id", target = "projectId")
    FolderResponse toResponse(Folder folder);
}
