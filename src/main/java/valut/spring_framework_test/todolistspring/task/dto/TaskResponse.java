package valut.spring_framework_test.todolistspring.task.dto;

import java.time.LocalDate;
import valut.spring_framework_test.todolistspring.task.model.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status
) {
}
