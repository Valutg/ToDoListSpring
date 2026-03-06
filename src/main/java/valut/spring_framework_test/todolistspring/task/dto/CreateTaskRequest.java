package valut.spring_framework_test.todolistspring.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank(message = "Название не должно быть пустым")
        @Size(max = 255, message = "Название должно содержать не более 255 символов")
        String title,

        @Size(max = 2000, message = "Описание должно содержать не более 2000 символов")
        String description,

        @NotNull(message = "Срок выполнения обязателен")
        @FutureOrPresent(message = "Срок выполнения не может быть в прошлом")
        LocalDate dueDate
) {
}
