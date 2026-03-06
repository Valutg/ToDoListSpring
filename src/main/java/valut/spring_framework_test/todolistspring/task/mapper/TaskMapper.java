package valut.spring_framework_test.todolistspring.task.mapper;

import valut.spring_framework_test.todolistspring.task.dto.CreateTaskRequest;
import valut.spring_framework_test.todolistspring.task.dto.TaskResponse;
import valut.spring_framework_test.todolistspring.task.model.Task;
import valut.spring_framework_test.todolistspring.task.model.TaskStatus;

public final class TaskMapper {

    private TaskMapper() {
        throw new UnsupportedOperationException("Экземпляр служебного класса создавать нельзя");
    }

    public static Task toEntity(CreateTaskRequest request) {
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .status(TaskStatus.TODO)
                .build();
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus()
        );
    }
}
