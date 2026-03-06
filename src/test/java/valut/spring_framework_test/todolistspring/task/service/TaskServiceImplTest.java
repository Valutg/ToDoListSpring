package valut.spring_framework_test.todolistspring.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import valut.spring_framework_test.todolistspring.task.dto.CreateTaskRequest;
import valut.spring_framework_test.todolistspring.task.dto.SortDirection;
import valut.spring_framework_test.todolistspring.task.dto.TaskResponse;
import valut.spring_framework_test.todolistspring.task.dto.TaskSortBy;
import valut.spring_framework_test.todolistspring.task.dto.UpdateTaskRequest;
import valut.spring_framework_test.todolistspring.task.model.Task;
import valut.spring_framework_test.todolistspring.task.model.TaskStatus;
import valut.spring_framework_test.todolistspring.task.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTask_shouldCreateTaskWithDefaultTodoStatus() {
        CreateTaskRequest request = new CreateTaskRequest(
                "Написать документацию",
                "Подготовить описание API",
                LocalDate.now().plusDays(2)
        );

        when(taskRepository.save(any(Task.class))).thenReturn(Task.builder()
                .id(1L)
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .status(TaskStatus.TODO)
                .build());

        TaskResponse response = taskService.createTask(request);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());

        Task savedArgument = captor.getValue();
        assertEquals(TaskStatus.TODO, savedArgument.getStatus());
        assertNotNull(response.id());
        assertEquals("Написать документацию", response.title());
        assertEquals(TaskStatus.TODO, response.status());
    }

    @Test
    void updateTask_shouldUpdateAllFields() {
        Long taskId = 42L;
        Task existingTask = Task.builder()
                .id(taskId)
                .title("Старое название")
                .description("Старое описание")
                .dueDate(LocalDate.now().plusDays(1))
                .status(TaskStatus.TODO)
                .build();
        UpdateTaskRequest request = new UpdateTaskRequest(
                "Новое название",
                "Новое описание",
                LocalDate.now().plusDays(5),
                TaskStatus.DONE
        );

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        TaskResponse response = taskService.updateTask(taskId, request);

        assertEquals("Новое название", existingTask.getTitle());
        assertEquals("Новое описание", existingTask.getDescription());
        assertEquals(TaskStatus.DONE, existingTask.getStatus());
        assertEquals("Новое название", response.title());
        assertEquals(TaskStatus.DONE, response.status());
    }

    @Test
    void deleteTask_shouldDeleteExistingTask() {
        Long taskId = 5L;
        Task existingTask = Task.builder()
                .id(taskId)
                .title("Задача")
                .description("Описание")
                .dueDate(LocalDate.now().plusDays(3))
                .status(TaskStatus.IN_PROGRESS)
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        doNothing().when(taskRepository).delete(existingTask);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).delete(existingTask);
    }

    @Test
    void getTasks_shouldFilterByStatusAndApplySorting() {
        TaskStatus statusFilter = TaskStatus.IN_PROGRESS;
        Sort sort = Sort.by(Sort.Direction.ASC, "dueDate");
        List<Task> tasks = List.of(
                Task.builder()
                        .id(1L)
                        .title("Задача А")
                        .description("Описание А")
                        .dueDate(LocalDate.now().plusDays(1))
                        .status(TaskStatus.IN_PROGRESS)
                        .build(),
                Task.builder()
                        .id(2L)
                        .title("Задача Б")
                        .description("Описание Б")
                        .dueDate(LocalDate.now().plusDays(2))
                        .status(TaskStatus.IN_PROGRESS)
                        .build()
        );

        when(taskRepository.findByStatus(statusFilter, sort)).thenReturn(tasks);

        List<TaskResponse> response = taskService.getTasks(statusFilter, TaskSortBy.DUE_DATE, SortDirection.ASC);

        assertEquals(2, response.size());
        assertEquals("Задача А", response.get(0).title());
        verify(taskRepository).findByStatus(statusFilter, sort);
    }
}
