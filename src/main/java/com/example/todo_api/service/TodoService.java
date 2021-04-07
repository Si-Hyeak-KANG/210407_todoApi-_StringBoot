package com.example.todo_api.service;

import com.example.todo_api.model.TodoEntity;
import com.example.todo_api.model.TodoRequest;
import com.example.todo_api.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    //request를 받아 Entity에 추가
    public TodoEntity add(TodoRequest request) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setCompleted(request.getCompleted());
        return this.todoRepository.save(todoEntity);//save는 제네릭을 받은 타입을 반환

    }

    //Entity에서 찾고자 하는 id를 받아 검색
    public TodoEntity searchById(Long id) {
        return this.todoRepository.findById(id)//제네릭을 받는 타입 반환
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));//404.
                //찾는 id가 없는 경우 예외 코드 전송

    }

    //Entity 목록 전체를 조회
    public List<TodoEntity> searchAll() {
        return this.todoRepository.findAll();
    }

    //Entity에서 수정할 id를 검색 후 request로 수정
    public TodoEntity updateById(Long id, TodoRequest todoRequest) {
        TodoEntity todoEntity = this.searchById(id);
        if(todoRequest.getTitle() != null) {
            todoEntity.setTitle(todoRequest.getTitle());
        }

        if(todoRequest.getOrder() != null) {
            todoEntity.setOrder(todoRequest.getOrder());
        }
        if(todoRequest.getCompleted() != null) {
            todoEntity.setCompleted(todoRequest.getCompleted());
        }
        return this.todoRepository.save(todoEntity);
    }

    //id 하나만 삭제
    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);//반환값이 없는 메서드
    }

    //id 전체 삭제
   public void deleteAll() {
        this.todoRepository.deleteAll();
   }


}

