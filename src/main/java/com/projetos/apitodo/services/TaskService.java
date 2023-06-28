package com.projetos.apitodo.services;

import com.projetos.apitodo.models.Task;
import com.projetos.apitodo.models.User;
import com.projetos.apitodo.repositories.TaskRepository;
import com.projetos.apitodo.services.exceptions.DataBindingViolationException;
import com.projetos.apitodo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public Task findById(Long id){

        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada. ID: " +id+ ", Tipo: " + Task.class.getName()
        ));

    }

    public List<Task> findAllByUserId(Long user_id){

        List<Task> tasks = this.taskRepository.findByUserId(user_id);
        return tasks;

    }

    @Transactional
    public Task create(Task task){

        User user = this.userService.findById(task.getUser().getId());
        task.setId(null);
        task.setUser(user);
        task = this.taskRepository.save(task);

        return task;
    }

    @Transactional
    public Task update(Task task){

        Task newTask = findById(task.getId());
        newTask.setDescription(task.getDescription());

        return this.taskRepository.save(newTask);

    }

    public void delete(Long id){

        findById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e){
            throw new DataBindingViolationException("Tarefa vinculada a outras entidades. Não foi possível excluir!");
        }
    }

}
