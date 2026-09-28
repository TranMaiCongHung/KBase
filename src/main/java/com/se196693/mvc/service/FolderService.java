package com.se196693.mvc.service;

import com.se196693.mvc.dto.request.FolderCreationRequest;
import com.se196693.mvc.dto.request.FolderUpdateRequest;
import com.se196693.mvc.dto.response.FolderResponse;
import com.se196693.mvc.entity.Folder;

import java.util.List;

public interface FolderService {

    FolderResponse createFolder(Long projectId, FolderCreationRequest request);

    FolderResponse updateFolder(FolderUpdateRequest request);

    List<FolderResponse> getFolders(Long projectId);

    FolderResponse getDetailsFolder(Long id);

    void deleteFolder(Long projectId, Long folderId);

    Folder getFolderById(Long id);
}
