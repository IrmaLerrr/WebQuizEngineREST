package engine.dto;

import java.util.Collections;
import java.util.List;

public record QuizAnswer(List<Integer> answer) {
    @Override
    public List<Integer> answer() {
        return answer != null ? answer : Collections.emptyList();
    }
}

