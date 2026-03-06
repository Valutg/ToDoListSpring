package valut.spring_framework_test.todolistspring.task.dto;

import java.util.Arrays;

public enum TaskSortBy {
    DUE_DATE("dueDate"),
    STATUS("status");

    private final String value;

    TaskSortBy(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static TaskSortBy from(String rawValue) {
        return Arrays.stream(values())
                .filter(sortBy -> sortBy.value.equalsIgnoreCase(rawValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Некорректный параметр sortBy: " + rawValue + ". Допустимые значения: dueDate, status"
                ));
    }
}
