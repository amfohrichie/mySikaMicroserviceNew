package com.mysikabox.task_service.controller;

import com.mysikabox.task_service.Handlers.ResponseHandler;
import com.mysikabox.task_service.entities.Task;
import com.mysikabox.task_service.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create task", description = "Creates the task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Task created Successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Task.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try{
            Task createdTask = taskService.createTask(task);
            return ResponseHandler.handleResponse("Successfully created task", HttpStatus.OK,createdTask);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/fetchTaskById/{id}")
    @Operation(summary = "Fetch task", description = "Get tasks")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Fetched Successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Task.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try{
            Task task = taskService.getTaskById(id);
            return ResponseHandler.handleResponse("Task retrieved successfully", HttpStatus.OK,task);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }

    }


    @Operation(summary = "Update task", description = "Updates the task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Task Updated Successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Task.class)
                                    )
                            }
                    )
            }
    )
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
    @Operation(summary = "Delete task", description = "Deletes the task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Task deleted Successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Task.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try{
            taskService.deleteTask(id);
            return ResponseHandler.handleResponse("Successfully deleted task", HttpStatus.OK,null);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/fetchAllTask")
    @Operation(summary = "Fetch all task", description = "Fetches all the task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Fetches all tasks successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Task.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<?> getAllTasks() {
        try{
            List<Task> tasks = taskService.getAllTasks();
            return ResponseHandler.handleResponse("Success", HttpStatus.OK,tasks);
        }catch (Exception e){
            return ResponseHandler.handleResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
