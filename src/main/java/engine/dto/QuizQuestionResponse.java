package engine.dto;

import engine.model.QuizQuestion;

import java.util.List;

public record QuizQuestionResponse(
        Integer id, String title, String text, List<String> options
) {
    public QuizQuestionResponse(QuizQuestion model) {
        this(model.id(), model.title(), model.text(), model.options());
    }
}
