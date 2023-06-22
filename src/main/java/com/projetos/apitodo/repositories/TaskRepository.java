package com.projetos.apitodo.repositories;

import com.projetos.apitodo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task>  findByUser_Id(Long id);

    /* JPQL
    @Query(value = "SELECT t from Task t where t.user.id = :user_id")
    List<Task> findByUser_id(@Param("user_id") Long id);

       SQL
    @Query(value = "select * from task t where t.user_id = :user_id", nativeQuery = true)
    List<Task> findByUser_id(@Param("user_id") Long id);
    */
}
