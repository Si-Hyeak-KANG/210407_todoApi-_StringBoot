package com.example.todo_api.service;

import com.example.todo_api.model.TodoEntity;
import com.example.todo_api.model.TodoRequest;
import com.example.todo_api.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

// Mock을 사용하는 이유
// 1. 외부 시스템에 의존하지 않고 자체 테스트
// 2. 실제 데이터를 사용하게 되면 DB수정이 많아짐. 민감한 정보까지 변경될 수 있음.
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock //Mock 객체
    private TodoRepository todoRepository;

    @InjectMocks //이 Mock을 주입받아서 사용할
    private TodoService todoService;

    @Test
    void add() {
        when(this.todoRepository.save(any(TodoEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test Title");

        TodoEntity actual = this.todoService.add(expected);

        assertEquals(expected.getTitle(),actual.getTitle());
    }

    @Test
    void searchById() {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(123L);
        todoEntity.setTitle("TITLE");
        todoEntity.setOrder(0L);
        todoEntity.setCompleted(false);
        Optional<TodoEntity> optional = Optional.of(todoEntity);
        given(this.todoRepository.findById(anyLong()))
                .willReturn(optional);

        TodoEntity actual = this.todoService.searchById(123L);
        TodoEntity expected = optional.get();
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getOrder(),actual.getOrder());
        assertEquals(expected.getCompleted(),actual.getCompleted());
    }
}