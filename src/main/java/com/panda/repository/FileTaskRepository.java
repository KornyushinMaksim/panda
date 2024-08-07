package com.panda.repository;

import com.panda.model.FileTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileTaskRepository extends JpaRepository<FileTask, UUID> {
}
