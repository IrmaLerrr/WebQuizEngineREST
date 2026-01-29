package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engine.dto.QuizQuestionRequest;

import java.util.Collections;
import java.util.List;

public record QuizQuestion(
        Integer id, String title, String text, List<String> options, @JsonIgnore List<Integer> answer
) {
    public QuizQuestion(int id, QuizQuestionRequest request) {
        this(id, request.title(), request.text(),
                request.options(), request.answer());
    }
    @Override
    public List<Integer> answer() {
        return answer != null ? answer : Collections.emptyList();
    }
}