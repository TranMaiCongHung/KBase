package com.se196693.mvc.service.impl;

import com.se196693.mvc.dto.request.FolderCreationRequest;
import com.se196693.mvc.dto.request.FolderUpdateRequest;
import com.se196693.mvc.dto.response.FolderResponse;
import com.se196693.mvc.entity.Folder;
import com.se196693.mvc.entity.Project;
import com.se196693.mvc.exception.ResourceNotFoundException;
import com.se196693.mvc.mapper.FolderMapper;
import com.se196693.mvc.repository.FolderRepository;
import com.se196693.mvc.service.FolderService;
import com.se196693.mvc.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderServiceimpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;
    private final ProjectService projectService;
    @Override
    public FolderResponse createFolder(Long projectId, FolderCreationRequest request) {
        Project project = projectService.findProjectById(projectId);
        Folder folder = folderMapper.toEntity(request);
        folder.setProject(project);
        folderRepository.save(folder);
        return folderMapper.toResponse(folder);
    }

    @Override
    public FolderResponse updateFolder(FolderUpdateRequest request) {
        return null;
    }

    @Override
    public List<FolderResponse> getFolders(Long projectId) {
        List<Folder> folders = folderRepository.findByProjectId(projectId);

        return folders.stream()
                .map(folderMapper::toResponse)
                .toList();
    }

    @Override
    public FolderResponse getDetailsFolder(Long id) {
        return folderMapper.toResponse(getFolderById(id));
    }

    @Override
    public void deleteFolder(Long projectId, Long folderId) {
        folderRepository.delete(getFolderById(folderId));
    }

    @Override
    public Folder getFolderById(Long id) {
        Folder foundFolder = folderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Folder is not found")
        );
        return foundFolder;
    }
}
