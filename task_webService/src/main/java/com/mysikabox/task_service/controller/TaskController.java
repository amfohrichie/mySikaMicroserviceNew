package com.mysikabox.task_service.controller;

import com.mysikabox.task_service.Handlers.ResponseHandler;
import com.mysikabox.task_service.entities.Task;
import com.mysikabox.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try{
            Task createdTask = taskService.createTask(task);
            return ResponseHandler.handleResponse("Successfully created task", HttpStatus.OK,createdTask);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/fetchTaskById/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try{
            Task task = taskService.getTaskById(id);
            return ResponseHandler.handleResponse("Task retrieved successfully", HttpStatus.OK,task);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }

    }

    @PutMapping("updateTask/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        try{
            Task task = taskService.updateTask(id, updatedTask);
            return ResponseHandler.handleResponse("Successfully updated task", HttpStatus.OK,task);

        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try{
            taskService.deleteTask(id);
            return ResponseHandler.handleResponse("Successfully deleted task", HttpStatus.OK,null);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/fetchAllTask")
    public ResponseEntity<?> getAllTasks() {
        try{
            List<Task> tasks = taskService.getAllTasks();
            return ResponseHandler.handleResponse("Success", HttpStatus.OK,tasks);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
