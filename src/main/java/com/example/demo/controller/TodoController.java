package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        List<String> list = new ArrayList<>();
        list.add(todoService.testService());
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String temporaryUserId = "temporary-user";

            TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);

            todoEntity.setId(null);

            todoEntity.setUserId(temporaryUserId);

            List<TodoEntity> todoEntities = todoService.create(todoEntity);
            List<TodoDTO> todoDTOS = todoEntities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(todoDTOS).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";

        List<TodoEntity> todoEntities = todoService.retrieve(temporaryUserId);
        List<TodoDTO> todoDTOS = todoEntities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(todoDTOS).build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO) {
        String temporaryUserId = "temporary-user";

        TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
        todoEntity.setUserId(temporaryUserId);

        List<TodoEntity> todoEntities = todoService.update(todoEntity);
        List<TodoDTO> todoDTOS = todoEntities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(todoDTOS).build();
        return ResponseEntity.ok().body(responseDTO);
    }
}
