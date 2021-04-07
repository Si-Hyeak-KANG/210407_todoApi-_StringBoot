package com.example.todo_api.repository;
//Repository(저장소) => 여기서는 데이터베이스와 데이터를 주고 받기 위한 인터페이스를 정의한 부분
import com.example.todo_api.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//<Entity Type, key(id)>
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
