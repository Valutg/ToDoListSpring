package valut.spring_framework_test.todolistspring.task.service;

import java.util.List;
import valut.spring_framework_test.todolistspring.task.dto.CreateTaskRequest;
import valut.spring_framework_test.todolistspring.task.dto.SortDirection;
import valut.spring_framework_test.todolistspring.task.dto.TaskResponse;
import valut.spring_framework_test.todolistspring.task.dto.TaskSortBy;
import valut.spring_framework_test.todolistspring.task.dto.UpdateTaskRequest;
import valut.spring_framework_test.todolistspring.task.model.TaskStatus;

public interface TaskService {

    List<TaskResponse> getTasks(TaskStatus status, TaskSortBy sortBy, SortDirection direction);

    TaskResponse getTaskById(Long taskId);

    TaskResponse createTask(CreateTaskRequest request);

    TaskResponse updateTask(Long taskId, UpdateTaskRequest request);

    void deleteTask(Long taskId);
}
