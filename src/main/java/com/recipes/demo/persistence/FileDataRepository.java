package com.recipes.demo.persistence;

import com.recipes.demo.businesslayer.FileData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FileDataRepository extends CrudRepository<FileData, Long> {
    boolean existsByName(String name);
    Optional<FileData> findByName(String fileName);
}
