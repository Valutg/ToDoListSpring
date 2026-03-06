package valut.spring_framework_test.todolistspring.task.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import valut.spring_framework_test.todolistspring.task.model.Task;
import valut.spring_framework_test.todolistspring.task.model.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status, Sort sort);
}
