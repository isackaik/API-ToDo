package com.projetos.apitodo.controllers;

import com.projetos.apitodo.models.Task;
import com.projetos.apitodo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){

        Task task = this.taskService.findById(id);
        return ResponseEntity.ok().body(task);

    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long user_id){

        List<Task> tasks = this.taskService.findAllByUserId(user_id);
        return ResponseEntity.ok().body(tasks);

    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task task) {

        this.taskService.create(task);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
                .buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task task, Long id){

        task.setId(id);
        this.taskService.update(task);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        this.taskService.delete(id);
        return ResponseEntity.noContent().build();

    }




}
