package valut.spring_framework_test.todolistspring.task.dto;

import java.util.Arrays;
import org.springframework.data.domain.Sort;

public enum SortDirection {
    ASC,
    DESC;

    public Sort.Direction toSpringDirection() {
        return this == ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public static SortDirection from(String rawValue) {
        return Arrays.stream(values())
                .filter(direction -> direction.name().equalsIgnoreCase(rawValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Некорректный параметр direction: " + rawValue + ". Допустимые значения: asc, desc"
                ));
    }
}
