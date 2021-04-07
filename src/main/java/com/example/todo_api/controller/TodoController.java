package com.example.todo_api.controller;

import com.example.todo_api.model.TodoEntity;
import com.example.todo_api.model.TodoRequest;
import com.example.todo_api.model.TodoResponse;
import com.example.todo_api.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin//CORS issue 방지
@AllArgsConstructor
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest todoRequest) {
        System.out.println("Create");

        if (ObjectUtils.isEmpty(todoRequest.getTitle()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(todoRequest.getOrder()))
            todoRequest.setOrder(0L);

        if (ObjectUtils.isEmpty(todoRequest.getCompleted()))
            todoRequest.setCompleted(false);

        TodoEntity result = this.todoService.add(todoRequest);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        System.out.println("READ ONE");

        TodoEntity result = this.todoService.searchById(id);

        return ResponseEntity.ok(new TodoResponse(result));

    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        System.out.println("READ ALL");

        List<TodoEntity> lists = this.todoService.searchAll();
        List<TodoResponse> response = lists.stream().map(TodoResponse::new)
                                                    .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        System.out.println("UPDATE");
        TodoEntity result = this.todoService.updateById(id, todoRequest);

        return ResponseEntity.ok(new TodoResponse(result));
    }
    // PUT : 리소스의 모든 것을 업데이트 한다. 보내지 않은 값은 NULL
    // PATCH : 리소스의 일부를 업데이트 한다.

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        System.out.println("DELETE");
        this.todoService.deleteById(id);

        return ResponseEntity.ok().build();//
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        System.out.println("DELETE ALL");
        this.todoService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
