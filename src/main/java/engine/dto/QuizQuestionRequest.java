package engine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuizQuestionRequest(
        @NotBlank String title, @NotBlank String text, @Size(min = 2) @NotNull List<String> options, List<Integer> answer
) {
}
