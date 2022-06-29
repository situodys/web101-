package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService(){
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    /*public String createTodoItem(TodoEntity todoEntity){
        repository.save(todoEntity);
        return todoEntity.getTitle();
    }*/
    private void validate(final TodoEntity todoEntity) {
        //validation
        if (todoEntity == null) {
            log.warn("entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (todoEntity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<TodoEntity> create(final TodoEntity todoEntity) {
        validate((todoEntity));
        repository.save(todoEntity);

        log.info("Entity Id : {} is saved", todoEntity.getId());

        return repository.findByUserId((todoEntity.getUserId()));
    }

    /*public TodoEntity retrieveTodoItem(String id){
        TodoEntity todoEntity = repository.findById(id).get();
        return todoEntity;
    }*/

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity todoEntity) {
        validate(todoEntity);

        Optional<TodoEntity> original = repository.findById(todoEntity.getId());;

        original.ifPresent(todo ->{
            todo.setTitle(todoEntity.getTitle());
            todo.setDone(todoEntity.isDone());

            repository.save(todo);
        } );

        return retrieve(todoEntity.getUserId());
    }
}
