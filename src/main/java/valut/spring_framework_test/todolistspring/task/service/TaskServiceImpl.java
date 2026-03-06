package valut.spring_framework_test.todolistspring.task.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import valut.spring_framework_test.todolistspring.task.dto.CreateTaskRequest;
import valut.spring_framework_test.todolistspring.task.dto.SortDirection;
import valut.spring_framework_test.todolistspring.task.dto.TaskResponse;
import valut.spring_framework_test.todolistspring.task.dto.TaskSortBy;
import valut.spring_framework_test.todolistspring.task.dto.UpdateTaskRequest;
import valut.spring_framework_test.todolistspring.task.exception.ResourceNotFoundException;
import valut.spring_framework_test.todolistspring.task.mapper.TaskMapper;
import valut.spring_framework_test.todolistspring.task.model.Task;
import valut.spring_framework_test.todolistspring.task.model.TaskStatus;
import valut.spring_framework_test.todolistspring.task.repository.TaskRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<TaskResponse> getTasks(TaskStatus status, TaskSortBy sortBy, SortDirection direction) {
        Sort sort = Sort.by(direction.toSpringDirection(), sortBy.value());
        List<Task> tasks = status == null
                ? taskRepository.findAll(sort)
                : taskRepository.findByStatus(status, sort);

        return tasks.stream().map(TaskMapper::toResponse).toList();
    }

    @Override
    public TaskResponse getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .map(TaskMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Задача с id=%d не найдена".formatted(taskId)));
    }

    @Override
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = TaskMapper.toEntity(request);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toResponse(savedTask);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request) {
        Task task = getTaskOrThrow(taskId);
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setStatus(request.status());

        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toResponse(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        Task task = getTaskOrThrow(taskId);
        taskRepository.delete(task);
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Задача с id=%d не найдена".formatted(taskId)));
    }
}
