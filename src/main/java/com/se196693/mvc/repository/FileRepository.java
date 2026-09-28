package com.se196693.mvc.repository;

import com.se196693.mvc.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFolderId(Long folderId);
}
