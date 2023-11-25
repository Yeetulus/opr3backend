package com.osu.opr3.opr3project.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t " +
            "WHERE t.parentTask IS NULL " +
            "AND t.category.id IN :categoryIds")
    List<Task> findTasksByUserAndCategoryIds(List<Long> categoryIds);
}
